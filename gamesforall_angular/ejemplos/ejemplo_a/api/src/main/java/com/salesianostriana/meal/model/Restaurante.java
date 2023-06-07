package com.salesianostriana.meal.model;

import com.salesianostriana.meal.security.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NamedEntityGraph(name = "restaurante-con-platos", attributeNodes = @NamedAttributeNode(value = "platos"))
public class Restaurante {

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

    private String nombre;
    private String descripcion;
    private String coverImgUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurante")
    @Builder.Default
    private List<Plato> platos = new ArrayList<>();

    private LocalTime apertura;
    private LocalTime cierre;

    @ManyToOne
    private User restaurantAdmin;

    @ManyToMany
    private List<Cocina> cocina;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
