package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLikesResponse {
    private String username, fullname;
    private List<RecipeResponse> favorites;

    public static UserLikesResponse fromUser(User user){
        return UserLikesResponse.builder()
                .username(user.getUsername())
                .fullname(user.getFullname())
                .favorites(user.getFavorites()
                        .stream()
                        .map(RecipeResponse::fromRecipe)
                        .collect(Collectors.toList()))
                .build();
    }
}
