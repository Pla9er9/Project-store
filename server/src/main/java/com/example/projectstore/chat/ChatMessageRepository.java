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
    @Query
    Page<ChatMessage> findAllByChatIdOrderBySendDateTimeDesc(String chatId, Pageable pageable);

    @Query("SELECT DISTINCT m.sender.username FROM ChatMessage m WHERE m.recipient.username = :username AND m.sender.username != :username")
    List<String> findChatsOpenedByRecipient(@Param("username") String username);

    @Query("SELECT DISTINCT m.recipient.username FROM ChatMessage m WHERE m.sender.username = :username AND m.recipient.username != :username")
    List<String> findChatsOpenedBySender(@Param("username") String username);
}
