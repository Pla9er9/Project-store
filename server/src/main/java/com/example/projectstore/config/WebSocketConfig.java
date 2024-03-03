package com.example.projectstore.config;

import com.example.projectstore.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173/")
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(2048 * 2048);
        registration.setSendBufferSizeLimit(2048 * 2048);
        registration.setSendTimeLimit(2048 * 2048);
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean factoryBean = new ServletServerContainerFactoryBean();
        factoryBean.setMaxTextMessageBufferSize(2048 * 2048);
        factoryBean.setMaxBinaryMessageBufferSize(2048 * 2048);
        factoryBean.setMaxSessionIdleTimeout(2048L * 2048L);
        factoryBean.setAsyncSendTimeout(2048L * 2048L);
        return factoryBean;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                List<String> tokenList = accessor.getNativeHeader("Authorization");
                String token;
                if (tokenList == null || tokenList.isEmpty()) {
                    return message;
                } else {
                    token = tokenList.get(0);
                    if (token == null) {
                        return message;
                    }
                }

                try {
                    var username = jwtService.extractUsername(token);
                    userRepository.findByUsername(username)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                } catch (Exception e) {
                    log.info("HERE !!!");
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }

                accessor.setLeaveMutable(true);
                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            }
        });

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}
