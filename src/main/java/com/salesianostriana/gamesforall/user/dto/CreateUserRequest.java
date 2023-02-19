package com.salesianostriana.gamesforall.user.dto;

import com.salesianostriana.gamesforall.zvalidation.annotation.FieldsValueMatch;
import com.salesianostriana.gamesforall.zvalidation.annotation.StrongPassword;
import com.salesianostriana.gamesforall.zvalidation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsValueMatch(field = "password",fieldMatch = "verifyPassword")
public class CreateUserRequest {

    @UniqueUsername
    private String username;

    @StrongPassword
    private String password;
    private String verifyPassword;
    private String avatar;
    private String fullName;

}
