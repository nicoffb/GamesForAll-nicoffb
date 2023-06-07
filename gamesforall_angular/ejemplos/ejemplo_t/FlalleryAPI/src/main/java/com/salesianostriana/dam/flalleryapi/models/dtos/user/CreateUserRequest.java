package com.salesianostriana.dam.flalleryapi.models.dtos.user;

import com.salesianostriana.dam.flalleryapi.validation.annotation.FieldsValueMatch;
import com.salesianostriana.dam.flalleryapi.validation.annotation.StrongPassword;
import com.salesianostriana.dam.flalleryapi.validation.annotation.UniqueUsername;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password", fieldMatch = "verifyPassword",
                message = "{createUserRequest.password.nomatch}"
        )
})
public class CreateUserRequest {

    @UniqueUsername(message = "{createUserRequest.username.unique}")
    @NotEmpty(message = "{createUserRequest.username.notempty}")
    private String username;

    @StrongPassword
    @NotEmpty(message = "{createUserRequest.password.notempty}")
    private String password;

    @NotEmpty(message = "{createUserRequest.verifypassword.notempty}")
    private String verifyPassword;

    @URL(message = "{createUserRequest.avatar.url}")
    private String avatar;

    @NotEmpty(message = "{createUserRequest.fullname.notempty}")
    private String fullName;

}
