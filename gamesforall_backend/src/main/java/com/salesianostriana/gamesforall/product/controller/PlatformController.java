package com.salesianostriana.gamesforall.product.controller;


import com.salesianostriana.gamesforall.exception.UserNotFoundException;
import com.salesianostriana.gamesforall.product.dto.*;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.service.PlatformService;
import com.salesianostriana.gamesforall.search.util.Extractor;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @GetMapping("/list")
    public List<PlatformDTO> getPlatforms( ) {

        List<PlatformDTO> platforms = platformService.searchPlatforms();

        return platforms;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlatform(@PathVariable Long id) {
        platformService.deletePlatform(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<PlatformDTO> createNewPlatform(@RequestBody PlatformDTO created) {


        Platform platform = platformService.createPlatform(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(platform.getId()).toUri();

        PlatformDTO converted = PlatformDTO.of(platform);


        return ResponseEntity
                .created(createdURI)
                .body(converted);

    }



}
