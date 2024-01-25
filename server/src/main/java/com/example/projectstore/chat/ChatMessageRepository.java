package com.example.projectstore.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    @Query("SELECT c FROM ChatMessage c WHERE c.chatId = ?1")
    List<ChatMessage> findAllByChatId(String chatId);
}
