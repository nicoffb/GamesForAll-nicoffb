package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientRequest {
    @NotEmpty(message = "{ingredientRequest.name.notempty}")
    private String name;
    @Size(max = 254, message = "{recipeRequest.ingredient.sizemax}")
    private String description;
    private double amount;
    private String unit;
}
