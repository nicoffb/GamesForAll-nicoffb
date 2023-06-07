package com.salesianostriana.dam.flalleryapi.models.dtos.user;

import com.salesianostriana.dam.flalleryapi.validation.annotation.StrongPassword;
import com.salesianostriana.dam.flalleryapi.validation.annotation.UniqueUsername;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEditRequest {

    private UUID id;
    @URL(message = "{createUserRequest.avatar.url}")
    private String avatar;

    @NotEmpty(message = "{createUserRequest.fullname.notempty}")
    private String fullName;
}
