package com.salesianostriana.gamesforall.chat.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class ChatPK implements Serializable {


    private UUID emisor_id;
    private UUID receptor_id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatPK messagePK)) return false;
        return emisor_id.equals(messagePK.emisor_id) && receptor_id.equals(messagePK.receptor_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emisor_id, receptor_id);
    }
}
