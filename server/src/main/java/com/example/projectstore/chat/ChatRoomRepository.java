package com.example.projectstore.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    @Query("""
            SELECT c FROM ChatRoom c WHERE (c.sender.username = :senderUsername AND c.recipient.username = :recipientUsername) OR
            (c.sender.username = :recipientUsername AND c.recipient.username = :senderUsername)""")
    Optional<ChatRoom> findChatroom(@Param("senderUsername") String senderUsername, @Param("recipientUsername") String recipientUsername);
}
