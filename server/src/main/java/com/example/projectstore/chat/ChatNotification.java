package com.example.projectstore.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private UUID id;
    private String senderId;
    private String recipientId;
    private String content;
}
