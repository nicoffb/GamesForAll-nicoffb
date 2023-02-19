package com.salesianostriana.gamesforall.product.dto;

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
    //la fecha se deberia poner sola cuando se crea
    @NotBlank
    private String category;


    public Product toProduct(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.price)
                .category(productRequestDTO.category)
                .build();
    }

}
