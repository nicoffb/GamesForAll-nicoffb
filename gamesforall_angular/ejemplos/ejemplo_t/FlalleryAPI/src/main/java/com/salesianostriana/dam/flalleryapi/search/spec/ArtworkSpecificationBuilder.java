package com.salesianostriana.dam.flalleryapi.search.spec;

import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteria;
import com.salesianostriana.dam.flalleryapi.models.Artwork;

import java.util.List;

public class ArtworkSpecificationBuilder extends GenericSpecificationBuilder<Artwork> {
    public ArtworkSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Artwork.class);
    }
}
