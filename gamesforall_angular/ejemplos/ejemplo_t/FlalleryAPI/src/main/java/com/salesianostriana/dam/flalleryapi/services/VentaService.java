package com.salesianostriana.dam.flalleryapi.services;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Venta;
import com.salesianostriana.dam.flalleryapi.repositories.ArtworkRepository;
import com.salesianostriana.dam.flalleryapi.repositories.VentaRepository;
import com.salesianostriana.dam.flalleryapi.search.spec.VentaSpecificationBuilder;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ArtworkRepository artworkRepository;

    public Page<Venta> search(List<SearchCriteria> params, Pageable pageable) {

        VentaSpecificationBuilder artworkSpecificationBuilder =
                new VentaSpecificationBuilder(params);

        Specification<Venta> spec =  artworkSpecificationBuilder.build();
        return ventaRepository.findAll(spec, pageable);
    }


    public Optional<Venta> findVentaByID (Long id) {return ventaRepository.findById(id);};


    public Venta add(Venta venta) {

        Optional<Artwork> artwork = artworkRepository.findById(venta.getArtwork().getIdArtwork());
        artwork.get().setDisponibleParaComprar(Boolean.FALSE);
        artworkRepository.save(artwork.get());
        return ventaRepository.save(venta);
    }


    public void deleteVenta(Venta venta) {
        Optional<Artwork> artwork = artworkRepository.findById(venta.getArtwork().getIdArtwork());
        artwork.get().setDisponibleParaComprar(Boolean.TRUE);
        artworkRepository.save(artwork.get());
        ventaRepository.delete(venta);}
}
