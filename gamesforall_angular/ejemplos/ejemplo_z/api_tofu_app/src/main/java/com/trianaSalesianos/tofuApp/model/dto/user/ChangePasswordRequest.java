package com.trianaSalesianos.tofuApp.model.dto.user;

import com.trianaSalesianos.tofuApp.validation.annotation.FieldsValueMatch;
import com.trianaSalesianos.tofuApp.validation.annotation.StrongPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "newPassword", fieldMatch = "verifyNewPassword",
                message = "{FieldsValueMatch.verifyPassword}"
        )
})
public class ChangePasswordRequest {
    private String oldPassword;
    @StrongPassword
    private String newPassword;
    private String verifyNewPassword;
}
