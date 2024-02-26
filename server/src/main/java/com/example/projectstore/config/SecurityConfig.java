package com.example.projectstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] AUTH_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/")).permitAll()
                .antMatchers(AUTH_LIST).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v2/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/ws")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/ws/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/cdn/images/*")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/dev/application/*/name", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/oauth2/account", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/report", "POST")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/search/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/status")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*/issues", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*/issues/*", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/issue/*", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*/download", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*/files", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/project/*/release", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/*", "GET")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/*/avatar", "GET")).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cookie"));
        configuration.setExposedHeaders(List.of("Cookie"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
