package com.salesianostriana.gamesforall.message.model;

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

public class MessagePK implements Serializable {

    private UUID emisor_id;
    private UUID receptor_id;
}
