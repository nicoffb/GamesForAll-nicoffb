package com.salesianostriana.gamesforall.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUserResponse extends UserResponse {

    private String token;
    private String refreshToken;

    public JwtUserResponse(UserResponse userResponse) {
        id = userResponse.getId();
        username = userResponse.getUsername();
        fullName = userResponse.getFullName();
        avatar = userResponse.getAvatar();
        createdAt = userResponse.getCreatedAt();
    }

    public static JwtUserResponse of (User user, String token, String refreshToken) {
        JwtUserResponse result = new JwtUserResponse(fromUser(user));
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        return result;

    }

}
