package com.trianaSalesianos.tofuApp.model.dto.category;

import com.trianaSalesianos.tofuApp.validation.annotation.UniqueCategory;
import com.trianaSalesianos.tofuApp.validation.annotation.ValidHexColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @UniqueCategory
    private String name;
    @ValidHexColor
    private String color;
}
