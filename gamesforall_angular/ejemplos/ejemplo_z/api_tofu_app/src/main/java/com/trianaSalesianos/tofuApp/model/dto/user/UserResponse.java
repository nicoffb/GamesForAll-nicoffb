package com.trianaSalesianos.tofuApp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponse {

    protected UUID id;
    protected String username, avatar, fullname, description;




    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .description(user.getDescription())
                .build();
    }
}
