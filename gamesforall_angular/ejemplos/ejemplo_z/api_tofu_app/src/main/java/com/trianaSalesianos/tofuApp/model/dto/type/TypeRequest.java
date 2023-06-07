package com.trianaSalesianos.tofuApp.model.dto.type;

import com.trianaSalesianos.tofuApp.validation.annotation.UniqueType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeRequest {
    @UniqueType
    private String name;

}
