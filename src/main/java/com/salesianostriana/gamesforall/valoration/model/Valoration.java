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
    @JoinColumn(name = "user_review", foreignKey = @ForeignKey(name = "FK_VALORATION_REVIEWER"),columnDefinition = "uuid")
    private User reviewer;

    @ManyToOne
    @MapsId("reviewed_user_id")
    @JoinColumn(name = "reviewed_user", foreignKey = @ForeignKey(name = "FK_VALORATION_REVIEWED"),columnDefinition = "uuid")
    private User reviewedUser;


    public void setUserReview(User userReviewer) {
        this.reviewer = userReviewer;
    }

    public void setReviewedUser(User reviewedUser) {
        this.reviewedUser = reviewedUser;
    }

    public void setPK(ValorationPK valorationPK){
        this.id = valorationPK;
    }

}



