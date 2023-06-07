package com.salesianostriana.meal.model;

import com.salesianostriana.meal.model.key.LineaVentaPK;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LineaVenta {

    @EmbeddedId
    private LineaVentaPK pk;

    @ManyToOne
    @MapsId("ventaId")
    private Venta venta;

    @ManyToOne
    @MapsId("platoId")
    private Plato plato;

    private double precioUnidad;

    private int cantidad;

    private double subtotal;
/*
    @ManyToOne
    private Descuento descuento;

    private double subtotalNoOferta;
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaVenta that = (LineaVenta) o;
        return pk.equals(that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
