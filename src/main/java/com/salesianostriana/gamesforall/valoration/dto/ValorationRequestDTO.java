package com.salesianostriana.gamesforall.valoration.dto;

import com.salesianostriana.gamesforall.valoration.model.Valoration;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class ValorationRequestDTO {


    private double score;
    private String review;
//    private UUID loggedUser;
//    private UUID targetUser;

    public Valoration toValoration (ValorationRequestDTO valorationRequestDTO){
        return Valoration.builder()
                .score(valorationRequestDTO.getScore())
                .review(valorationRequestDTO.getReview())
                .build();
    }

}
