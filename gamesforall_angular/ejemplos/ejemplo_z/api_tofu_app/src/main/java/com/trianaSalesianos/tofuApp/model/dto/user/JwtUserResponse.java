package com.trianaSalesianos.tofuApp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trianaSalesianos.tofuApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUserResponse extends UserResponse{
    private String token;
    private String refreshToken;


    public JwtUserResponse(UserResponse userResponse) {
        username = userResponse.getUsername();
        fullname = userResponse.getFullname();
        avatar = userResponse.getAvatar();
        description = userResponse.getDescription();
    }

    public static JwtUserResponse of (User user, String token, String refreshToken) {
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        return result;

    }
}
