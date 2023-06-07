package com.salesianostriana.meal.model.dto.venta;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.LineaVenta;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.view.View;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LineaVentaResponseDTO {

    @JsonView({View.VentaView.VentaDetailView.class})
    private PlatoResponseDTO plato;

    @JsonView({View.VentaView.VentaDetailView.class})
    private double precioUnidad;

    @JsonView({View.VentaView.VentaDetailView.class})
    private int cantidad;

    @JsonView({View.VentaView.VentaDetailView.class})
    private double subtotal;

    public static LineaVentaResponseDTO of(LineaVenta lineaVenta){
        return LineaVentaResponseDTO.builder()
                .cantidad(lineaVenta.getCantidad())
                .precioUnidad(lineaVenta.getPrecioUnidad())
                .subtotal(lineaVenta.getSubtotal())
                .plato(PlatoResponseDTO.of(lineaVenta.getPlato()))
                .build();
    }



}
