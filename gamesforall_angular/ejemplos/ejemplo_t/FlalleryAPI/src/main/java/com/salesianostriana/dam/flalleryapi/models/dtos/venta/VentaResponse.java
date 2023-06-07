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
public class VentaResponse {

    private String artworkName;
    private double precio;
    private String fechaVenta;
    private String usernameComprador;
    private String usernameVendedor;

    public static VentaResponse ventaToVentaResponse(Venta v) {
        return VentaResponse.builder()
                .usernameComprador(v.getUsernameComprador())
                .fechaVenta(v.getFechaVenta().toString())
                .artworkName(v.getArtwork().getName())
                .precio(v.getPrecio())
                .usernameVendedor(v.getUsernameVendedor())
                .build();
    };
}
