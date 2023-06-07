package com.trianaSalesianos.tofuApp.model.dto.recipe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RecipeResponse {
    private UUID id;
    private String name, img, type;
    private Integer prepTime, nLikes;
    private UserResponse author;

    private List<CategoryResponse> categories;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static RecipeResponse fromRecipe(Recipe recipe){
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .categories(recipe.getCategories()
                        .stream()
                        .map(CategoryResponse::fromCategory)
                        .collect(Collectors.toList()))
                .img(recipe.getImg())
                .author(UserResponse.fromUser(recipe.getAuthor()))
                .prepTime(recipe.getPrepTime())
                .type(recipe.getType().getName())
                .nLikes(recipe.getFavoritedBy().size())
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}
