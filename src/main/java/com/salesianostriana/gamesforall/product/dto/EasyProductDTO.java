package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EasyProductDTO {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String state;
    private String image;

    public static EasyProductDTO of(Product product){
        return EasyProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .state(product.getState().getValue())
                .image(product.getImage())
                .build();
    }

}
