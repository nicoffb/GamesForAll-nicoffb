package com.salesianostriana.gamesforall.user.dto;


import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserRequest {

    private UUID id;

    private String avatar;

    private String fullName;
}
