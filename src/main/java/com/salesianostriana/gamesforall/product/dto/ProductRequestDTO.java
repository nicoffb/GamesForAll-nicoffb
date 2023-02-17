package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ProductRequestDTO {


    private String title;
    private String description;
    private String image;
    private double price;
    //la fecha se deberia poner sola cuando se crea
    private String category;


    public Product toProduct(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .image(productRequestDTO.getImage())
                .price(productRequestDTO.price)
                .category(productRequestDTO.category)
                .build();
    }

}
