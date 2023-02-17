package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class BasicProductDTO {


    private Long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private LocalDateTime publication_date;
    private String category;

    public static BasicProductDTO of(Product product){
        return BasicProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .publication_date(product.getPublication_date())
                .category(product.getCategory())
                .build();
    }


    public Product toProduct(BasicProductDTO basicProductDTO){
        return Product.builder()
                .title(basicProductDTO.getTitle())
                .description(basicProductDTO.getDescription())
                .image(basicProductDTO.getImage())
                .price(basicProductDTO.price)
                .category(basicProductDTO.category)
                .build();
    }
}
