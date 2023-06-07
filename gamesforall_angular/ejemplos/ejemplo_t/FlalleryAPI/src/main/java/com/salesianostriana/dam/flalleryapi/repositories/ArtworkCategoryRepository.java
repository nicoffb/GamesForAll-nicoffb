package com.salesianostriana.dam.flalleryapi.repositories;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtworkCategoryRepository extends JpaRepository<ArtworkCategory, Long> {

    Optional<ArtworkCategory> findFirstByName(String name);
}
