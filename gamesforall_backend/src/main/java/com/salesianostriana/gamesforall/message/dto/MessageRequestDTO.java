package com.salesianostriana.gamesforall.message.dto;

import com.salesianostriana.gamesforall.message.model.Message;
import lombok.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MessageRequestDTO {


    private String comment;

    public Message toMessage (MessageRequestDTO messageRequestDTO){
        return Message.builder()
                .comment(messageRequestDTO.getComment())
                .build();
    }

}
