package com.salesianostriana.gamesforall.chat.service;



import com.salesianostriana.gamesforall.chat.model.Chat;
import com.salesianostriana.gamesforall.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat add(Chat chat) {
        return chatRepository.save(chat);
    }

    public void delete(Chat chat) {
        chatRepository.delete(chat);
    }

    public List<Chat> findChatsByUserId(UUID userId) {
        return chatRepository.findChats(userId);
    }

}




