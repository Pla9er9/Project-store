package com.example.projectstore.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue
    private UUID id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
