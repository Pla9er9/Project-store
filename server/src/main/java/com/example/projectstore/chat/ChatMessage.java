package com.example.projectstore.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;
    private String chatId;
    private String content;
    private String senderUsername;
    private String recipientUsername;
    @JsonFormat(pattern = "hh-mm - dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @JsonSerialize
    private LocalDateTime sendDateTime;
    private String type;
}