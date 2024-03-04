package com.example.projectstore.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    @Query("SELECT c FROM ChatMessage c WHERE c.chatId = ?1")
    List<ChatMessage> findAllByChatId(String chatId);

    @Query
    Page<ChatMessage> findAllByChatIdOrderBySendDateTimeDesc(String chatId, Pageable pageable);

    @Query("SELECT DISTINCT m.senderUsername FROM ChatMessage m WHERE m.recipientUsername = :username AND m.senderUsername != :username")
    List<String> findChatsOpenedByRecipient(@Param("username") String username);

    @Query("SELECT DISTINCT m.recipientUsername FROM ChatMessage m WHERE m.senderUsername = :username AND m.recipientUsername != :username")
    List<String> findChatsOpenedBySender(@Param("username") String username);

}
