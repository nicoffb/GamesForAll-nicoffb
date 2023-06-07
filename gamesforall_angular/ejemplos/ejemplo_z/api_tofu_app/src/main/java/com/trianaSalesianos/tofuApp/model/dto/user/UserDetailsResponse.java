package com.trianaSalesianos.tofuApp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsResponse {
    private String username, avatar, fullname, email, description, birthday;
    private Integer nFollowers, nFollowing;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    public static UserDetailsResponse fromUser(User user){
        return UserDetailsResponse.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .description(user.getDescription())
                .birthday(user.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .createdAt(user.getCreatedAt())
                .nFollowers(user.getFollowers().size())
                .nFollowing(user.getFollowing().size())
                .build();
    }
}
