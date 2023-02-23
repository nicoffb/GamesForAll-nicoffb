package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.PlatformEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class BasicProductDTO {
//PONER VALIDACIONES PARA EL EDIT


    private String title;
    private String description;
    private String image;
    private double price;
    private LocalDateTime publication_date;
    private String category;
    private PlatformEnum platform;

    public static BasicProductDTO of(Product product){
        return BasicProductDTO.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .publication_date(product.getPublication_date())
                .platform(product.getPlatform())
                .category(product.getCategory())
                .build();
    }


    public Product toProduct(BasicProductDTO basicProductDTO){
        return Product.builder()
                .title(basicProductDTO.getTitle())
                .description(basicProductDTO.getDescription())
                .image(basicProductDTO.getImage())
                .price(basicProductDTO.getPrice())
                .publication_date(basicProductDTO.getPublication_date())
                .category(basicProductDTO.getCategory())
                .platform(basicProductDTO.getPlatform())
                .build();
    }
}
