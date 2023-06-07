package com.salesianostriana.dam.flalleryapi.models.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.salesianostriana.dam.flalleryapi.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUserResponse extends UserResponse {

    private String token;
    private String refreshToken;

    protected String role;
    private boolean enabled;
    public JwtUserResponse(UserResponse userResponse) {
        id = userResponse.getId();
        username = userResponse.getUsername();
        fullName = userResponse.getFullName();
        avatar = userResponse.getAvatar();
        createdAt = userResponse.getCreatedAt();
        role = userResponse.getRole();
    }

    public static JwtUserResponse of (User user, String token, String refreshToken) {
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        result.setRole(user.getRoles().toString().equals("[ADMIN]")?"Admin":"User");
        result.setEnabled(user.isEnabled());
        result.setRefreshToken(refreshToken);
        return result;

    }

}
