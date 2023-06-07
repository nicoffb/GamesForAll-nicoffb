package com.salesianostriana.meal.model.dto.plato;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class RateResponseDTO {

    @JsonView({View.PlatoView.PlatoDetailView.class})
    private String username;

    private String nombrePlato;
    @JsonView({View.PlatoView.PlatoDetailView.class})
    private double nota;
    @JsonView({View.PlatoView.PlatoDetailView.class})
    private String comentario;

    private LocalDateTime fecha;

    public static RateResponseDTO of(Valoracion valoracion){
        return RateResponseDTO.builder()
                .username(valoracion.getUsuario().getUsername())
                .nombrePlato(valoracion.getPlato().getNombre())
                .nota(valoracion.getNota())
                .comentario(valoracion.getComentario())
                .fecha(valoracion.getFecha())
                .build();
    }

}
