package com.salesianostriana.gamesforall.message.dto;

import com.salesianostriana.gamesforall.message.model.Message;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class MessageDTO {



    private String comment;
    private LocalDateTime message_date;  //posibilidad de que sea String para no hacer las conversiones en la vista y que de menos problemas
    private String emisorUser;
    private String receptorUser;


    public static MessageDTO of(Message message){
        return MessageDTO.builder()
                .comment(message.getComment())
                .message_date(message.getMessage_date())
                .emisorUser(message.getEmisor().getUsername())
                .receptorUser(message.getReceptor().getUsername())
                .build();
    }


//    public Valoration toValoration(ValorationDTO valorationDTO){
//        return Valoration.builder()
//                .
//                .build();
//    }
}
