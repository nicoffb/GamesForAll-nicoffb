package com.salesianostriana.dam.flalleryapi.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="artwork")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "Artwork.commentsAndLoved", attributeNodes = {
        @NamedAttributeNode("comments"),
        @NamedAttributeNode("usersThatLiked")
})
public class Artwork {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID idArtwork;

    private String name;

    private String imgUrl;

    private String description;
    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    private String owner;

    @OneToMany(mappedBy = "lovedArtwork", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Loved> usersThatLiked = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category",
            foreignKey = @ForeignKey(name="CATEGORY_ID_FK"))
    private ArtworkCategory category;

    private boolean disponibleParaComprar;


    @PreRemove
    public void deleteCommentFromArtwork(){

    }
}
