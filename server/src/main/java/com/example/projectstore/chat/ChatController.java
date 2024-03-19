package com.example.projectstore.chat;

import com.example.projectstore.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final FileService fileService;

    @GetMapping("/api/v1/messages/{senderUsername}/{recipientUsername}")
    public ResponseEntity<Page<ChatMessageDto>> getMessages(
            @PathVariable("senderUsername") String senderUsername,
            @PathVariable("recipientUsername") String recipientUsername,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
    ) {
        return ResponseEntity.ok(
                chatMessageService.getChatMessages(senderUsername, recipientUsername, page));
    }

    @GetMapping("/api/v1/cdn/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename, Authentication authentication) {
        return ResponseEntity.ok(
                chatMessageService.getImage(filename, authentication.getName()));
    }

    @MessageMapping("/chat")
    public void processMessage(
            @Valid @Payload ChatMessageDto chatMessage
    ) {
        chatMessage.setType("text");
        ChatMessage savedMsg = chatMessageService.saveMessage(chatMessage);

        if (savedMsg == null) {
            return;
        }

        var notification = new ChatNotification(
                savedMsg.getId(),
                chatMessage.getRecipientUsername(),
                chatMessage.getSenderUsername(),
                savedMsg.getContent(),
                "text",
                savedMsg.getSendDateTime()
        );

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientUsername(),
                "/queue/messages",
                notification
        );

        messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderUsername(),
                "/queue/messages",
                notification
        );
    }

    @MessageMapping("/chat/img")
    public ChatMessage processImageMessage(
            @Valid @Payload ChatImage chatImage
    ) {
        var allowedExtensions = List.of("png", "gif", "jpg");
        if (!allowedExtensions.contains(chatImage.getFileExtension())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var savedMsg = chatMessageService.saveImageMessage(chatImage);

        var notification = new ChatNotification(
                savedMsg.getId(),
                chatImage.getRecipientUsername(),
                chatImage.getSenderUsername(),
                savedMsg.getContent(),
                "image",
                savedMsg.getSendDateTime()
        );

        messagingTemplate.convertAndSendToUser(
                chatImage.getSenderUsername(),
                "/queue/messages",
                notification
        );

        messagingTemplate.convertAndSendToUser(
                chatImage.getRecipientUsername(),
                "/queue/messages",
                notification
        );

        return savedMsg;
    }
}
