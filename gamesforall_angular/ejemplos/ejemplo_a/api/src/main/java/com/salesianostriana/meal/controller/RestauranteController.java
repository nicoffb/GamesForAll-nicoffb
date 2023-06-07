package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.RestauranteFinderService;
import com.salesianostriana.meal.service.RestauranteService;
import com.salesianostriana.meal.service.storage.MediaUrlResource;
import com.salesianostriana.meal.service.storage.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

class PageRestauranteResponse extends PageDTO<RestauranteResponseDTO>{}
@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    private final RestauranteFinderService finderService;
    private final StorageService storageService;

    @Operation(summary = "Obtiene todos los restaurantes")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado restaurantes",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PageRestauranteResponse.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado restaurantes",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @GetMapping("/")
    @JsonView(View.RestauranteView.RestauranteGenericView.class)
    public PageDTO<RestauranteResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<RestauranteResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(RestauranteResponseDTO::of));
    }

    @Operation(summary = "Obtiene los detalles de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el restaurante",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RestauranteResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante", name = "id", required = true)
    @GetMapping("/{id}")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public RestauranteResponseDTO findById(@PathVariable UUID id){
        return RestauranteResponseDTO.of(service.findWithMenu(id));
    }

    @Operation(summary = "Crea un nuevo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el restaurante",
                    content = {@Content(schema = @Schema(implementation = RestauranteResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es un propietario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @PostMapping("/")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public ResponseEntity<RestauranteResponseDTO> create(@AuthenticationPrincipal User loggedUser,
                                                         @RequestPart("file") MultipartFile file,
                                                         @Valid @RequestPart("body") RestauranteRequestDTO restauranteDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestauranteResponseDTO.of(service.add(restauranteDto, loggedUser, file)));
    }

    @Operation(summary = "Obtiene la imagen de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el restaurante y su imagen",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante del cual buscar la imagen", name = "id", required = true)
    @GetMapping("/{id}/img/")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Restaurante restaurante = finderService.findById(id);
        if(restaurante.getCoverImgUrl() == null) throw new EntityNotFoundException();
        MediaUrlResource resource =
                (MediaUrlResource) storageService.loadAsResource(restaurante.getCoverImgUrl());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @Operation(summary = "Cambia la imagen de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha cambiado la imagen",
                    content = {@Content(schema = @Schema(implementation = RestauranteResponseDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante del cual cambiar la imagen", name = "id", required = true)
    @PutMapping("/{id}/img/")
    public RestauranteResponseDTO changeImage(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @RequestPart("file") MultipartFile file){
        return RestauranteResponseDTO.of(service.changeImg(loggedUser, id, file));
    }

    @Operation(summary = "Borra la imagen de un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la imagen con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante del cual borrar la imagen", name = "id", required = true)
    @DeleteMapping("/{id}/img/")
    public RestauranteResponseDTO deleteImg(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        return RestauranteResponseDTO.of(service.deleteImg(loggedUser, id));
    }

    @Operation(summary = "Borra un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el restaurante con éxito",
                    content = {}),
            @ApiResponse(responseCode = "400",
                    description = "El borrado se ha prohibido porque el restaurante aun tiene platos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el restaurante",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        service.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita un restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el restaurante",
                    content = {@Content(schema = @Schema(implementation = RestauranteResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante a editar",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante a editar", name = "id", required = true)
    @PutMapping("/{id}")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public RestauranteResponseDTO edit(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RestauranteRequestDTO restauranteDto){
        return RestauranteResponseDTO.of(service.edit(id, restauranteDto, loggedUser));
    }

    @Operation(summary = "Obtiene todos los restaurantes que el usuario gestiona")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado restaurantes",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PageRestauranteResponse.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado restaurantes",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es un propietario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class)))
    })
    @GetMapping("/managed")
    @JsonView(View.RestauranteView.RestauranteGenericView.class)
    public PageDTO<RestauranteResponseDTO> findManaged(@AuthenticationPrincipal User loggedUser,
                                                       @PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<RestauranteResponseDTO> result = new PageDTO<>();
        return result.of(service.findManaged(loggedUser, pageable).map(RestauranteResponseDTO::of));
    }

}
