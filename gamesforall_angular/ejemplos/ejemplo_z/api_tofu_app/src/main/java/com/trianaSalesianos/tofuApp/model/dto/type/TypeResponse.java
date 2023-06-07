package com.trianaSalesianos.tofuApp.model.dto.type;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Type;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TypeResponse {

    private UUID id;
    private String name;
    public static TypeResponse fromType(Type c){
        return TypeResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}
