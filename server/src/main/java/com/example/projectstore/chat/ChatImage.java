package com.example.projectstore.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatImage {
    private String chatId;
    private byte[] image;
    private String fileExtension;
    private String senderUsername;
    private String recipientUsername;
}
