package com.trianaSalesianos.tofuApp.model.dto.category;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryResponse {
    private UUID id;
    private String name, color;
    public static CategoryResponse fromCategory(Category c){
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .color(c.getColorCode())
                .build();
    }
}
