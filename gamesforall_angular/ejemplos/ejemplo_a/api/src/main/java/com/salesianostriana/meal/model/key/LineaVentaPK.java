package com.salesianostriana.meal.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineaVentaPK implements Serializable {

    public UUID ventaId;
    public UUID platoId;

}
