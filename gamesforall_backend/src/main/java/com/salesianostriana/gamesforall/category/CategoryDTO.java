package com.salesianostriana.gamesforall.category;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CategoryDTO {
//PONER VALIDACIONES PARA EL EDIT

    private Long id;
    private String genre;


    public static CategoryDTO of(Category category) {

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