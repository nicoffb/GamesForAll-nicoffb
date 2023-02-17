package com.salesianostriana.gamesforall.valoration.model;


import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="valoration")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Valoration {

    @EmbeddedId
    @Builder.Default
    private ValorationPK id = new ValorationPK();

    private double score;

    private String review;

    @ManyToOne
    @MapsId("user_review_id")
    @JoinColumn(name = "user_review_id",foreignKey = @ForeignKey(name = "FK_USER_VALORATION"))
    private User idUserR;

    @ManyToOne
    @MapsId("reviewed_user_id")
    @JoinColumn(name = "reviewed_user_id",foreignKey = @ForeignKey(name = "FK_VALORATION_USER"))
    private User idReviewedU;

    //o son objetos User o tipo Long?
    //como se vincula con user?
}
