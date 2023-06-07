package com.salesianostriana.meal.model;

import com.salesianostriana.meal.security.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToMany(mappedBy = "venta")
    private List<LineaVenta> lineas;

    private double gastoEnvio;

    private double totalPedido;

    private double total;
/*
    private OfertaEntrega oferta;

    private double totalNoOferta;

    private DescuentoEnvio descuentoEnvio;
*/
    private LocalDateTime fecha;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private DireccionEnvio direccionEnvio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return id.equals(venta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
