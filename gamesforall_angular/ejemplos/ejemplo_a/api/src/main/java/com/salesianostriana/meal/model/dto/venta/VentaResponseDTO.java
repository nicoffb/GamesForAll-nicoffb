package com.salesianostriana.meal.model.dto.venta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.DireccionEnvio;
import com.salesianostriana.meal.model.LineaVenta;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.Venta;
import com.salesianostriana.meal.model.dto.direccionEnvio.DireccionResponseDTO;
import com.salesianostriana.meal.model.dto.plato.RateResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.LazyInitializationException;

import javax.persistence.ManyToOne;
import javax.sound.sampled.Line;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
public class VentaResponseDTO {

    @JsonView({View.VentaView.VentaDetailView.class})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LineaVentaResponseDTO> lineas;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private double gastoEnvio;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private double totalPedido;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private double total;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private LocalDateTime fecha;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private UserResponse comprador;

    @JsonView({View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
    private DireccionResponseDTO direccionEnvio;

    public static VentaResponseDTO of(Venta venta){
        List<LineaVentaResponseDTO> lineas = new ArrayList<>();
        try{
            lineas = venta.getLineas().stream().map(LineaVentaResponseDTO::of).toList();
        }catch(LazyInitializationException exception){
            lineas = new ArrayList<>();
        }
        return VentaResponseDTO.builder()
                .lineas(lineas)
                .gastoEnvio(venta.getGastoEnvio())
                .totalPedido(venta.getTotalPedido())
                .total(venta.getTotal())
                .fecha(venta.getFecha())
                .comprador(UserResponse.fromUser(venta.getUsuario()))
                .direccionEnvio(DireccionResponseDTO.of(venta.getDireccionEnvio()))
                .build();
    }

}
