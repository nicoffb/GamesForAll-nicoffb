package com.trianaSalesianos.tofuApp.model.dto.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepRequest {
    @Size(max = 254, message = "{stepRequest.description.sizemax}")

    private String description;
}
