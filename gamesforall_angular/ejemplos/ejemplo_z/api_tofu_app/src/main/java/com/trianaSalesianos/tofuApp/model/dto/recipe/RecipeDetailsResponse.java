package com.trianaSalesianos.tofuApp.model.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.step.StepResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDetailsResponse {
    private UUID id;
    private String name, description, img, type;
    private Integer prepTime, nLikes;
    private List<CategoryResponse> categories;
    private List<RecipeIngredientResponse> ingredients;
    private UserResponse author;
    private List<StepResponse> steps;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static RecipeDetailsResponse fromRecipe(Recipe recipe){
        return RecipeDetailsResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .img(recipe.getImg())
                .author(UserResponse.fromUser(recipe.getAuthor()))
                .prepTime(recipe.getPrepTime())
                .nLikes(recipe.getFavoritedBy().size())
                .ingredients(recipe.getRecipeIngredients()
                        .stream()
                        .map(RecipeIngredientResponse::fromRecipeIngredient)
                        .collect(Collectors.toList()))
                .steps(recipe.getSteps()
                        .stream()
                        .map(StepResponse::fromStep)
                        .collect(Collectors.toList()))
                .categories(recipe.getCategories()
                        .stream()
                        .map(CategoryResponse::fromCategory)
                        .collect(Collectors.toList()))
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}
