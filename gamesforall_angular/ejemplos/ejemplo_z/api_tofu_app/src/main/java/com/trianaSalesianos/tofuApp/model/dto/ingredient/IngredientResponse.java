package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponse {
    private UUID id;
    private String name, img, description;
    private UserResponse author;
    public static IngredientResponse fromIngredient(Ingredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .img(ingredient.getImg())
                .description(ingredient.getDescription())
                .author(UserResponse.fromUser(ingredient.getAuthor()))
                .build();
    }
}
