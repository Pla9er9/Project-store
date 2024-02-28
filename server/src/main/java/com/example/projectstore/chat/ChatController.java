package com.example.projectstore.chat;

import com.example.projectstore.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final FileService fileService;

    @GetMapping("/api/v1/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ) {
        return ResponseEntity.ok(
                chatMessageService.getChatMessages(senderId, recipientId));
    }

    @GetMapping("/api/v1/cdn/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        return ResponseEntity.ok(fileService.getChatImage(filename));
    }

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ) {
        chatMessage.setType("text");
        chatMessage.setSendDateTime(LocalDateTime.now());
        ChatMessage savedMsg = chatMessageService.saveMessage(chatMessage);

        var notification = new ChatNotification(
                savedMsg.getId(),
                savedMsg.getRecipientUsername(),
                savedMsg.getSenderUsername(),
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
            @Payload ChatImage chatImage
    ) {
        var allowedExtensions = List.of("png", "gif", "jpg");
        if (!allowedExtensions.contains(chatImage.getFileExtension())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var id = UUID.randomUUID();

        fileService.saveNewChatImage(id, chatImage.getFileExtension(), chatImage.getImage());

        var chatMessage = new ChatMessage(
                id,
                chatImage.getChatId(),
                id + "." + chatImage.getFileExtension(),
                chatImage.getSenderUsername(),
                chatImage.getRecipientUsername(),
                LocalDateTime.now(),
                "image"
        );
        ChatMessage savedMsg = chatMessageService.saveMessage(chatMessage);

        var notification = new ChatNotification(
                savedMsg.getId(),
                savedMsg.getRecipientUsername(),
                savedMsg.getSenderUsername(),
                savedMsg.getContent(),
                "image",
                savedMsg.getSendDateTime()
        );

        messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderUsername(),
                "/queue/messages",
                notification
        );

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientUsername(),
                "/queue/messages",
                notification
        );

        return chatMessage;
    }
}
