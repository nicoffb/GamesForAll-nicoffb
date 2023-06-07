package com.salesianostriana.meal.model.dto.direccionEnvio;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.DireccionEnvio;
import com.salesianostriana.meal.model.view.View;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DireccionResponseDTO {

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String calle;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String numero;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String poblacion;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String ciudad;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String cp;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String puerta;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private String piso;

    public static DireccionResponseDTO of(DireccionEnvio direccionEnvio){
        return DireccionResponseDTO.builder()
                .calle(direccionEnvio.getCalle())
                .numero(direccionEnvio.getNumero())
                .poblacion(direccionEnvio.getPoblacion())
                .ciudad(direccionEnvio.getCiudad())
                .cp(direccionEnvio.getCp())
                .puerta(direccionEnvio.getPuerta())
                .piso(direccionEnvio.getPiso())
                .build();
    }


}
