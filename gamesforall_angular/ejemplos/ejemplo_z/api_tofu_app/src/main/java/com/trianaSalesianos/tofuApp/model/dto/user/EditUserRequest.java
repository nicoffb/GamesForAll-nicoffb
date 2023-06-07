package com.trianaSalesianos.tofuApp.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trianaSalesianos.tofuApp.validation.annotation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserRequest {
    @Email(message = "{editUserRequest.email.email}")
    private String email;
    @Size(max=32, message = "{editUserRequest.fullname.size}")
    private String fullname;
    @ValidDate(message = "{editUserRequest.birthday.validdate}")
    private String birthday;
    @Size(max=254, message = "{editUserRequest.description.size}")
    private String description;
}
