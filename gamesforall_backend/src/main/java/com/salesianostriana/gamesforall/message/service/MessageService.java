package com.salesianostriana.gamesforall.message.service;


import com.salesianostriana.gamesforall.message.model.Message;
import com.salesianostriana.gamesforall.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final MessageRepository repository;

    public Message add(Message message) {
        return repository.save(message);
    }


    public void delete(Message message) {
        repository.delete(message);
    }

//    public void deleteById(Long id) {
//        repository.deleteById(id); //no va porque validation no tiene id, sino clave compuesta
//    }


     public List<Message> findMessagesById (UUID userId){
       return repository.findMessagesById(userId);
     }


}
