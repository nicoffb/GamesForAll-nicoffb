package com.salesianostriana.dam.flalleryapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="artwork_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "ArtworkCategory.artwork", attributeNodes = {
        @NamedAttributeNode("artworkList")
})
@SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", initialValue = 3) // Change the initialValue as per your requirement
public class ArtworkCategory {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @Id
    private Long idCategory;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Artwork> artworkList;

    private String name;

    @PreRemove
    private void setCategorySinCategoria(){

        ArtworkCategory ac = ArtworkCategory.builder()
                .idCategory(0L)
                .name("Sin categoria")
                .build();

        for (Artwork a: artworkList) {
            a.setCategory(ac);
        };
    }
}
