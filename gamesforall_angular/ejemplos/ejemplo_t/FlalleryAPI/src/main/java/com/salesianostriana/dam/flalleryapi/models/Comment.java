package com.salesianostriana.dam.flalleryapi.models;

import com.salesianostriana.dam.flalleryapi.repositories.ArtworkRepository;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue
    private Long idComment;

    private String text;

    @ManyToOne
    @JoinColumn(name = "artwork", foreignKey = @ForeignKey(name="ARTWORK_ID_FK"))
    private Artwork artwork;

    private String writer;

    @PreRemove
    public void deleteCommentFromArtwork(){
        artwork.getComments().remove(this);
    }
}
