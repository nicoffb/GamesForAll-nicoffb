package com.salesianostriana.dam.flalleryapi.services;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import com.salesianostriana.dam.flalleryapi.models.dtos.artworkcategory.ArtworkCategoryCreateRequest;
import com.salesianostriana.dam.flalleryapi.repositories.ArtworkCategoryRepository;
import com.salesianostriana.dam.flalleryapi.repositories.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtworkCategoryService {

    private final ArtworkCategoryRepository artworkCategoryRepository;
    private final ArtworkRepository artworkRepository;

    public List<ArtworkCategory> findAll(){ return artworkCategoryRepository.findAll();}


    public Optional<ArtworkCategory> findById(Long id){ return  artworkCategoryRepository.findById(id);}


    public ArtworkCategory save(ArtworkCategory a){
        return artworkCategoryRepository.save(a);
    }


    public Optional<ArtworkCategory> edit(String name, ArtworkCategoryCreateRequest artworkCategoryEdit){
        return artworkCategoryRepository.findFirstByName(name)
                .map(c -> {
                    c.setName(artworkCategoryEdit.getName());
                    return artworkCategoryRepository.save(c);
        }).or(() -> Optional.empty());
    }

    public Optional<ArtworkCategory> findByName(String name){
        return artworkCategoryRepository.findFirstByName(name);
    }
    public void delete(ArtworkCategory ac) {
        ArtworkCategory sinCategoria;

        if (artworkCategoryRepository.findById(0L).isPresent()){
            sinCategoria = artworkCategoryRepository.findById(0L).get();

        }
        else {
            sinCategoria = ArtworkCategory.builder()
                    .idCategory(0L)
                    .name("Sin categoria")
                    .build();
            artworkCategoryRepository.save(sinCategoria);
        }

        for (Artwork a: ac.getArtworkList()
             ) {
            a.setCategory(sinCategoria);
            sinCategoria.getArtworkList().add(a);
        }

        artworkRepository.saveAll(ac.getArtworkList());
        artworkCategoryRepository.delete(ac);
        artworkCategoryRepository.save(sinCategoria);

    }
}
