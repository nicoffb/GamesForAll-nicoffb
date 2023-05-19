package com.salesianostriana.gamesforall.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class UserResponse {

    protected String id;
    protected String username, avatar, fullName;
    private String address;
    private double userScore;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;


    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .address(user.getAddress())
                .userScore(user.getTrades().stream().filter(p -> p.getSeller().equals(user)).mapToDouble(p -> p.getScore()).average().orElse(0))
                .build();
    }

    public User toUser(){
        return User.builder()
                .id(UUID.fromString(id))
                .username(username)
                .avatar(avatar)
                .fullName(fullName)
                .address(address)
                .build();
    }



}
