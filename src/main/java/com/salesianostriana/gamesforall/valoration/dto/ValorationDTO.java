package com.salesianostriana.gamesforall.valoration.dto;

import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.valoration.model.Valoration;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class ValorationDTO {



    private double score;
    private String comment;
    private String reviewedUser;
    private String reviewer;


    public static ValorationDTO of(Valoration valoration){
        return ValorationDTO.builder()
                .reviewedUser(valoration.getReviewedUser().getUsername())
                .score(valoration.getScore())
                .comment(valoration.getReview())
                .reviewer(valoration.getReviewer().getUsername())
                .build();
    }


//    public Valoration toValoration(ValorationDTO valorationDTO){
//        return Valoration.builder()
//                .
//                .build();
//    }
}
