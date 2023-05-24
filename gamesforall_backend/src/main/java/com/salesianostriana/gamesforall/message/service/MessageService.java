package com.salesianostriana.gamesforall.message.service;


import com.salesianostriana.gamesforall.message.model.Message;
import com.salesianostriana.gamesforall.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

//    public PageDto<ValorationDTO> findAllByReviewedUser(List<SearchCriteria> params, Pageable pageable, UUID userId){
//        VABuilder vaBuilder;
//        Specification<Valoration> spec;
//
////        if (params.isEmpty()){
////           spec = new ValorationSpecification("");
////        }else {
////            vaBuilder = new VABuilder(params);
////
////             spec = vaBuilder.build(); //no he hecho un ValorationSpecification, solo un VABuilder
////
////        }
//
//
//
//        //aqui devolvemos las valoraciones del id elegido pero sin criteria
//                                                                //aqui se cogen todas hayh que cambiarlo
//        Page<Valoration> pageValoration = repository.findAllByReviewedUserId(userId, spec, pageable);
//
//        if (pageValoration.isEmpty()) {
//            throw new EmptyProductListException();  //cambiar la excepcion a valoracion empty
//        }
//
//        Page<ValorationDTO> pageValorationDto = pageValoration.map(ValorationDTO::of);
//
//        return new PageDto<>(pageValorationDto);
//    }

     public Page<Message> findMessagesById (UUID userId, Pageable pageable){
       return repository.findMessagesById(userId, pageable);
     }


}
