package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.validation.annotation.StrongPassword;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
