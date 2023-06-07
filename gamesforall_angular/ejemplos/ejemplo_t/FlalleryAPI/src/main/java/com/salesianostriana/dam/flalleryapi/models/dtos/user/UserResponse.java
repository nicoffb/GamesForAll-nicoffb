package com.salesianostriana.dam.flalleryapi.models.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.salesianostriana.dam.flalleryapi.models.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class UserResponse {

    protected String id;
    protected String username, avatar, fullName;
    protected boolean enabled;

    protected String role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;


    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .role(user.getRoles().toString().equals("[ADMIN]")?"Admin":"User")
                .createdAt(user.getCreatedAt())
                .enabled(user.isEnabled())
                .build();
    }

}
