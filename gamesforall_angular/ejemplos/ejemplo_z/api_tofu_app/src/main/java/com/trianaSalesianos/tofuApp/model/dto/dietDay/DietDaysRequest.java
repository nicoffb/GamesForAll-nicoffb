package com.trianaSalesianos.tofuApp.model.dto.dietDay;

import com.trianaSalesianos.tofuApp.validation.annotation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietDaysRequest {
    @ValidDate(message = "{dietDaysRequest.day.validdate}")
    private String day;
}
