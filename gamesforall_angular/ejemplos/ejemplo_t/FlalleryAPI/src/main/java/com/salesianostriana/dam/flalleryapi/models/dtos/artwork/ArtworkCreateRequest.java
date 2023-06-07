package com.salesianostriana.dam.flalleryapi.models.dtos.artwork;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.Loved;
import jdk.jfr.Category;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkCreateRequest {

    @NotEmpty(message = "{artworkCreateRequest.name.notempty}")
    private String name;
    private String description;

    private String categoryName;

    public Artwork ArtworkCreateRequestToArtwork(ArtworkCategory category, String ownerName, String imgUrl) {
        return Artwork.builder()
                .owner(ownerName)
                .name(this.name)
                .imgUrl(imgUrl)
                .category(category)
                .disponibleParaComprar(Boolean.TRUE)
                .comments(new ArrayList<>())
                .usersThatLiked(new ArrayList<>())
                .description(this.description)
                .build();
    }

}
