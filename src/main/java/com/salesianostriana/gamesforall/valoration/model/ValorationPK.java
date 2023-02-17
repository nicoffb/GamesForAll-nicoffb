package com.salesianostriana.gamesforall.valoration.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class ValorationPK implements Serializable {

    private UUID user_review_id;
    private UUID reviewed_user_id;
}
