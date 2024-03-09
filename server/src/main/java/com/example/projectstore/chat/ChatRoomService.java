package com.example.projectstore.chat;

import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {

        return chatRoomRepository
                .findChatroom(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return Optional.empty();
                });
    }

    private String createChatId(String senderUsername, String recipientUsername) {
        var chatId = String.format("%s_%s", senderUsername, recipientUsername);

        var sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var recipient = userRepository.findByUsername(recipientUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .sender(sender)
                .recipient(recipient)
                .build();

        chatRoomRepository.save(senderRecipient);

        return chatId;
    }
}