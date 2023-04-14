package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Builder
@Value
public class ProductRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private double price;

    @NotBlank
    private String category;

    //ANOTACION PERSONALIZADA
    private String state;


    public Product toProduct(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                //.category(productRequestDTO.getCategory())
                .state(StateEnum.fromString(productRequestDTO.getState()))
                .build();
    }

}
