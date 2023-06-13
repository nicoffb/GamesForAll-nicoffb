package com.salesianostriana.gamesforall.platform;

import com.salesianostriana.gamesforall.category.CategoryDTO;
import com.salesianostriana.gamesforall.exception.*;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final ProductRepository productRepository;

    public List<PlatformDTO> searchPlatforms(){

        List<PlatformDTO> platformDTOS = platformRepository.findAll().stream()
                .map(PlatformDTO::of)
                .toList();

        if (platformDTOS.isEmpty()) {
            throw new EmptyPlatformListException();
        }

        return platformDTOS;
    }


    public Platform createPlatform(PlatformDTO platformDTO){

        Platform platform = platformDTO.toPlatform();
        platformRepository.save(platform);
        return platform;
    }


    public void deletePlatform(Long platformId){
        Platform platform = platformRepository.findById(platformId).orElseThrow(() -> new PlatformNotFoundException());
        List<Product> products = productRepository.findByPlatform(platform);
        products.forEach(product -> {
            product.setPlatform(null);
            productRepository.save(product);
        });
        platformRepository.delete(platform);
    }

    public PlatformDTO edit(Long id, PlatformDTO platformDTO){
        return platformRepository.findById(id)
                .map(platform -> {
                    platform.setPlatformName(platformDTO.getPlatformName());
                    platformRepository.save(platform);
                    PlatformDTO edited = platformDTO.of(platform);
                    return edited;
                })
                .orElseThrow(()->new PlatformNotFoundException());
    }



}
