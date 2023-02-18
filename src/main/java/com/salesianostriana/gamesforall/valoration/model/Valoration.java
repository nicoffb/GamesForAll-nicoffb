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
    @JoinColumn(name = "user_review_id", foreignKey = @ForeignKey(name = "FK_VALORATION_REVIEWER"))
    private User reviewer;

    @ManyToOne
    @MapsId("reviewed_user_id")
    @JoinColumn(name = "reviewed_user_id", foreignKey = @ForeignKey(name = "FK_VALORATION_REVIEWED"))
    private User reviewedUser;

    public Valoration(ValorationPK pk, double score, String review) {
    }

    public void setUserReview(User userReviewer) {
        this.id.setUser_review_id(userReviewer.getId());
        this.reviewer = userReviewer;
    }

    public void setReviewedUser(User reviewedUserN) {
        this.id.setReviewed_user_id(reviewedUserN.getId());
        this.reviewedUser = reviewedUser;
    }

}



