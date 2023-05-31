package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.*;
import com.salesianostriana.gamesforall.files.service.StorageService;
import com.salesianostriana.gamesforall.message.dto.MessageDTO;
import com.salesianostriana.gamesforall.product.dto.*;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.repository.PlatformRepository;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.search.specifications.GSBuilder;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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



}
