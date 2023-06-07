package com.trianaSalesianos.tofuApp.model.dto.ingredient;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientRequest {
    @NotEmpty(message = "{ingredientRequest.name.notempty}")
    private String name;
    @Size(max = 254, message = "{ingredientRequest.description.sizemax}")
    private String description;
}
