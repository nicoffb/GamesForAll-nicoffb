package com.salesianostriana.dam.flalleryapi.models.dtos.artworkcategory;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkCategoryCreateRequest {

    @NotEmpty(message = "{artworkCategoryCreateRequest.name.notempty}")
    private String name;

    public ArtworkCategory artworkCategoryCreateRequestToArtworkCategory () {
        ArtworkCategory artworkCategory = new ArtworkCategory();
        artworkCategory.setName(this.name);
        artworkCategory.setArtworkList(new ArrayList<Artwork>());
        return artworkCategory;
    }
}
