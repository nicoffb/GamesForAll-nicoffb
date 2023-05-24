package com.salesianostriana.gamesforall.message.model;


import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="messages")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @EmbeddedId
    @Builder.Default
    private MessagePK id = new MessagePK();

    private String comment;

    private LocalDateTime message_date;

    @ManyToOne(fetch = FetchType.LAZY)//eager
    @MapsId("emisor_id")
    @JoinColumn(name = "emisor", foreignKey = @ForeignKey(name = "FK_MESSAGE_EMISOR"),columnDefinition = "uuid")
    private User emisor;

    @ManyToOne(fetch = FetchType.LAZY)//eager
    @MapsId("receptor_id")
    @JoinColumn(name = "receptor", foreignKey = @ForeignKey(name = "FK_MESSAGE_RECEPTOR"),columnDefinition = "uuid")
    private User receptor;


    public void setEmisorUser(User emisorUser) {
        this.emisor = emisorUser;
    }

    public void setReceptorUser(User receptorUser) {
        this.receptor = receptorUser;
    }

    public void setPK(MessagePK messagePK){
        this.id = messagePK;
    }

}



