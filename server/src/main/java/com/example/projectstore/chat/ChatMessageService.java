package com.example.projectstore.chat;

import com.example.projectstore.file.FileService;
import com.example.projectstore.user.User;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMessage saveMessage(ChatMessageDto chatMessage) {
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderUsername(),
                chatMessage.getRecipientUsername(),
                true
        ).get();
        chatMessage.setChatId(chatId);

        var sender = userRepository.findByUsername(chatMessage.getSenderUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var recipient = userRepository.findByUsername(chatMessage.getRecipientUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        for (User u : recipient.getBlockedUsers()
        ) {
            if (u.getUsername().equals(sender.getUsername())) {
                messagingTemplate.convertAndSendToUser(
                        chatMessage.getSenderUsername(),
                        "/queue/messages",
                        "blocked"
                );
                return null;
            }
        }

        var msg = new ChatMessage(
                chatMessage.getId(),
                chatMessage.getChatId(),
                chatMessage.getContent(),
                sender,
                recipient,
                LocalDateTime.now(),
                chatMessage.getType()
        );

        return repository.save(msg);
    }

    public ChatMessage saveImageMessage(ChatImage chatImage) {
        var id = UUID.randomUUID();
        fileService.saveNewChatImage(id, chatImage.getFileExtension(), chatImage.getImage());
        var msg = new ChatMessageDto(
                id,
                null,
                id + "." + chatImage.getFileExtension(),
                chatImage.getSenderUsername(),
                chatImage.getRecipientUsername(),
                LocalDateTime.now(),
                "image"
        );
        return saveMessage(msg);
    }

    public Page<ChatMessageDto> getChatMessages(
            String senderId, String recipientId, int page
    ) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);

        return chatId.map(s -> repository.findAllByChatIdOrderBySendDateTimeDesc(s, PageRequest.of(page, 20))
                .map(this::chatMessageToDto)).orElseGet(Page::empty);
    }

    public Set<String> getUserChats(String name) {
        Set<String> res = new HashSet<>();
        res.addAll(repository.findChatsOpenedByRecipient(name));
        res.addAll(repository.findChatsOpenedBySender(name));
        return new HashSet<>(res);
    }

    public ChatMessageDto chatMessageToDto(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }

        return new ChatMessageDto(
                chatMessage.getId(),
                chatMessage.getChatId(),
                chatMessage.getContent(),
                chatMessage.getSender().getUsername(),
                chatMessage.getRecipient().getUsername(),
                chatMessage.getSendDateTime(),
                chatMessage.getType()
        );
    }
}