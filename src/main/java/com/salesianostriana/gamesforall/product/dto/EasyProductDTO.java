package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.PlatformEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EasyProductDTO {


    private String title;
    private String description;
    private double price;
    private String platform;
    private String image;

    public static EasyProductDTO of(Product product){
        return EasyProductDTO.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                //pasamos a String
                .platform(product.getPlatform().getDisplayName())
                .image(product.getImage())
                .build();
    }

}
