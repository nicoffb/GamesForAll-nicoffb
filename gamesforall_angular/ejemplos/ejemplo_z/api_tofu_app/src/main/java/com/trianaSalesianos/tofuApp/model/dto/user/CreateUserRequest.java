package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.validation.annotation.FieldsValueMatch;
import com.trianaSalesianos.tofuApp.validation.annotation.StrongPassword;
import com.trianaSalesianos.tofuApp.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password", fieldMatch = "verifyPassword",
                message = "{FieldsValueMatch.verifyPassword}"
        ),
        @FieldsValueMatch(
                field = "email", fieldMatch = "verifyEmail",
                message = "{FieldsValueMatch.verifyEmail}"
        )
})
public class CreateUserRequest {
    @UniqueUsername(message = "{createUserRequest.username.uniqueusername}")
    @NotEmpty(message = "{createUserRequest.username.notempty}")
    private String username;
    @StrongPassword
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;
    @NotEmpty(message = "{createUserRequest.verifypassword.notempty}")
    private String verifyPassword;
    @Email(message = "{createUserRequest.email.email}")
    @NotEmpty(message = "{createUserRequest.email.notempty}")
    private String email;
    @NotEmpty(message = "{createUserRequest.verifyemail.notempty}")
    private String verifyEmail;
    @NotEmpty(message = "{createUserRequest.fullname.notempty}")
    private String fullname;
}
