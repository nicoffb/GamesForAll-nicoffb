package com.salesianostriana.meal.model.dto.restaurante;

import com.salesianostriana.meal.model.Restaurante;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@Value
public class RestauranteRequestDTO {

    @NotBlank(message = "{RestauranteRequestDTO.nombre.notBlank}")
    private String nombre;

    @NotBlank(message = "{RestauranteRequestDTO.descripcion.notBlank}")
    @Size(max = 200, message = "{RestauranteRequestDTO.descripcion.size}")
    private String descripcion;

    private LocalTime apertura;
    private LocalTime cierre;

    private List<UUID> cocinas;

    public Restaurante toRestaurante(){
        return Restaurante.builder()
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .apertura(this.apertura)
                .cierre(this.cierre)
                .build();
    }

}
