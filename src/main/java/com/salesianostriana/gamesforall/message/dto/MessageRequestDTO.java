package com.salesianostriana.gamesforall.message.dto;

import com.salesianostriana.gamesforall.message.model.Message;
import lombok.Builder;
import lombok.Value;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Builder
@Value
public class MessageRequestDTO {


    private String comment;
    private LocalDateTime message_date;
//    private UUID loggedUser;
//    private UUID targetUser;

    public Message toMessage (MessageRequestDTO messageRequestDTO){
        return Message.builder()
                .comment(messageRequestDTO.getComment())
                .message_date(LocalDateTime.now())
                .build();
    }

}
