package com.example.projectstore.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @GetMapping("/api/v1/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ) {
        return ResponseEntity.ok(
                chatMessageService.getChatMessages(senderId, recipientId));
    }

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessage chatMessage
    ) {
        chatMessage.setSendDateTime(LocalDateTime.now());
        ChatMessage savedMsg = chatMessageService.saveMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientUsername(),
                "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderUsername(),
                        savedMsg.getRecipientUsername(),
                        savedMsg.getContent()
                )
        );
    }
}
