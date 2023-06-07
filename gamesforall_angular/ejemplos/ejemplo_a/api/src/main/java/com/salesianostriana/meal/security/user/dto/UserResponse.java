package com.salesianostriana.meal.security.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.Roles;
import com.salesianostriana.meal.security.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class UserResponse {

    protected String id;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    protected String username, nombre, email;
    protected Set<Roles> roles;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;


    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .nombre(user.getNombre())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

}
