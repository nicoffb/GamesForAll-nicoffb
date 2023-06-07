package com.trianaSalesianos.tofuApp.model.dto.step;

import com.trianaSalesianos.tofuApp.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StepResponse {
    private UUID id;
    private Integer stepNumber;
    private String description, recipeName;

    public static StepResponse fromStep(Step s) {
        String recipeName = s.getRecipe().getName();

        return StepResponse.builder()
                .id(s.getId())
                .description(s.getDescription())
                .stepNumber(s.getStepNumber())
                .recipeName(recipeName)
                .build();
    }
}
