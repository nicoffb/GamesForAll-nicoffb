package com.salesianostriana.meal.security.user.dto;

import com.salesianostriana.meal.validation.annotation.FieldsDiffer;
import com.salesianostriana.meal.validation.annotation.FieldsMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@FieldsMatch(field = "newPassword", fieldMatch = "verifyNewPassword")
@FieldsDiffer(field = "oldPassword", secondField = "newPassword")
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String verifyNewPassword;

}
