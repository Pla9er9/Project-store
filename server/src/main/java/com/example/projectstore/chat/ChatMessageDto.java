package com.example.projectstore.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private UUID id;
    private String chatId;
    @NotNull
    private String content;
    @NotNull
    private String senderUsername;
    @NotNull
    private String recipientUsername;
    @JsonFormat(pattern = "hh-mm - dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @JsonSerialize
    private LocalDateTime sendDateTime;
    private String type;
}
