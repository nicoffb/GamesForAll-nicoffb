package com.salesianostriana.meal.model.dto.plato;

import com.salesianostriana.meal.model.Plato;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Builder
@Value
public class PlatoRequestDTO {

    @NotBlank(message = "")
    private String nombre;

    @NotBlank(message = "")
    private String descripcion;

    @Positive(message = "")
    private double precio;

    @NotEmpty
    private List<String> ingredientes;

    @NotNull
    private boolean sinGluten;


    public Plato toPlato(){
        return Plato.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .ingredientes(ingredientes)
                .sinGluten(sinGluten)
                .build();
    }

}
