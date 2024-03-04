package com.example.projectstore.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderUsername(),
                chatMessage.getRecipientUsername(),
                true
        ).get();
        chatMessage.setChatId(chatId);
        return repository.save(chatMessage);
    }

    public Page<ChatMessage> getChatMessages(
            String senderId, String recipientId, int page
    ) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);
        if (chatId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return repository.findAllByChatIdOrderBySendDateTimeDesc(chatId.get(), PageRequest.of(page, 20));
    }

    public Set<String> getUserChats(String name) {
        Set<String> res = new HashSet<>();
        res.addAll(repository.findChatsOpenedByRecipient(name));
        res.addAll(repository.findChatsOpenedBySender(name));
        return new HashSet<>(res);
    }
}