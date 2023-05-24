package com.salesianostriana.gamesforall.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.gamesforall.message.model.Message;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class MessageDTO {



    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime message_date;
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
