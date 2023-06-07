package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.RecipeIngredient;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientResponse {
    private IngredientResponse ingredient;
    double amount;
    String unit, recipeName;

    public static RecipeIngredientResponse fromRecipeIngredient(RecipeIngredient r){
        return RecipeIngredientResponse.builder()
                .ingredient(IngredientResponse.fromIngredient(r.getIngredient()))
                .amount(r.getAmount())
                .unit(r.getUnit())
                .recipeName(r.getRecipe().getName())
                .build();
    }
}
