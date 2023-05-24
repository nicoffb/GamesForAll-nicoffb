package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Value
public class EasyProductDTO {

    private Long id;
    private String title;
    private String image;
    private double price;
    private LocalDateTime publication_date;
    private String state;
    private String platform;
    private Set<String> category;

    public static EasyProductDTO of(Product product){

        Set<String> genres = new HashSet<>();
        for (Category category : product.getCategories()) {
            genres.add(category.getGenre());
        }
        return EasyProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .image(product.getImage())
                .price(product.getPrice())
                .publication_date(product.getPublication_date())
                .state(product.getState().getValue())
                .platform(product.getPlatform().getPlatformName())
                .category(genres)
                .build();
    }

}
