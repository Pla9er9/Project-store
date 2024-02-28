package com.example.projectstore.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatImage {
    private String chatId;
    @NotNull
    private byte[] image;
    @NotNull
    private String fileExtension;
    @NotNull
    private String senderUsername;
    @NotNull
    private String recipientUsername;
}
