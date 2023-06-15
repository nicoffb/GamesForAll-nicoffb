package com.salesianostriana.gamesforall.platform;


import com.salesianostriana.gamesforall.category.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;


    @Operation(summary = "Obtiene todos las plataformas")
    @GetMapping("/list")
    public List<PlatformDTO> getPlatforms( ) {

        List<PlatformDTO> platforms = platformService.searchPlatforms();

        return platforms;

    }

    @Operation(summary = "Crea una plataforma")
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

    @Operation(summary = "Elimina una plataforma")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlatform(@PathVariable Long id) {
        platformService.deletePlatform(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Edita una plataforma")
    @PutMapping("/{id}")
    public PlatformDTO editPlatform(@PathVariable Long id, @RequestBody PlatformDTO edited) {
        return platformService.edit(id,edited);
    }


}
