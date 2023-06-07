package com.salesianostriana.meal.model;

import com.salesianostriana.meal.model.key.ValoracionPK;
import com.salesianostriana.meal.security.user.User;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Valoracion {

    public Valoracion(ValoracionPK pk) {
        this.pk = pk;
    }

    @EmbeddedId
    private ValoracionPK pk;

    @ManyToOne
    @MapsId("userId")
    private User usuario;

    @ManyToOne
    @MapsId("platoId")
    private Plato plato;

    private double nota;
    private String comentario;

    private LocalDateTime fecha;

    public void addUser(User user){
        this.usuario = user;
        user.getValoraciones().add(this);
    }

    public void removeUser(){
        usuario.getValoraciones().remove(this);
        this.usuario = null;
    }

    public void addPlato(Plato plato){
        this.plato = plato;
        plato.getValoraciones().add(this);
    }

    public void removePlato(){
        plato.getValoraciones().remove(this);
        this.plato = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valoracion that = (Valoracion) o;
        return pk.equals(that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
