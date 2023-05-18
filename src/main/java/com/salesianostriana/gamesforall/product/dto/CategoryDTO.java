package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Category;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
public class CategoryDTO {
//PONER VALIDACIONES PARA EL EDIT

    private Long id;
    private String genre;


    public CategoryDTO of(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .genre(category.getGenre())
                .build();
    }

    public Category toCategory (){
        return Category.builder()
                .id(id)
                .genre(genre)
                .build();
    }

}