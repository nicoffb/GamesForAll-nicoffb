package com.salesianostriana.gamesforall.message.model;


import com.salesianostriana.gamesforall.chat.model.Chat;
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

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    private LocalDateTime message_date;

    @ManyToOne(fetch = FetchType.LAZY)//eager
    @JoinColumn(name = "emisor_id", foreignKey = @ForeignKey(name = "FK_MESSAGE_EMISOR"),columnDefinition = "uuid")
    private User emisor;

    @ManyToOne(fetch = FetchType.LAZY)//eager
    @JoinColumn(name = "receptor_id", foreignKey = @ForeignKey(name = "FK_MESSAGE_RECEPTOR"),columnDefinition = "uuid")
    private User receptor;


    public void setEmisorUser(User emisorUser) {
        this.emisor = emisorUser;
    }

    public void setReceptorUser(User receptorUser) {
        this.receptor = receptorUser;
    }

}



