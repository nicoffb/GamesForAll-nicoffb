package com.salesianostriana.dam.flalleryapi.models.dtos.venta;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Venta;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaCreateRequest {

    private String artworkName;
    private double precio;
    private LocalDate fechaVenta;
    private String usernameComprador;
    private String usernameVendedor;

    public Venta ventaCreateRequestToVenta(Artwork a) {
        return Venta.builder()
                .usernameComprador(this.usernameComprador)
                .fechaVenta(this.getFechaVenta())
                .artwork(a)
                .precio(this.getPrecio())
                .usernameVendedor(this.getUsernameVendedor())
                .build();
    };
}
