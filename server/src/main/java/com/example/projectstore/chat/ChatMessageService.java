package com.example.projectstore.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        chatMessage.setChatId(chatId);
        return repository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(
            String senderId, String recipientId
    ) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);
        if (chatId.isEmpty()) {
            return new ArrayList<ChatMessage>();
        }
        return repository.findAllByChatId(chatId.get());
    }
}