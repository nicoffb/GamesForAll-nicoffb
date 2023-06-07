package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.error.exception.InvalidSearchException;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.plato.RateRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.search.Criteria;
import com.salesianostriana.meal.search.Utilities;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.PlatoService;
import com.salesianostriana.meal.service.storage.MediaUrlResource;
import com.salesianostriana.meal.service.storage.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import java.security.InvalidAlgorithmParameterException;
import java.util.List;
import java.util.UUID;

class PagePlatoResponse extends PageDTO<PlatoResponseDTO>{}
@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService service;
    private final StorageService storageService;

    @Operation(summary = "Obtiene todos los platos o busca entre ellos")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado platos",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PagePlatoResponse.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado platos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class)))
    })
    @GetMapping("/")
    @JsonView(View.PlatoView.PlatoGenericView.class)
    public PageDTO<PlatoResponseDTO> search(@RequestParam(value = "search", defaultValue = "")String search,
                                             @PageableDefault(page = 0, size = 10) Pageable pageable){
        List<Criteria> criterios = Utilities.extractCriteria(search);
        if (!criterios.stream().allMatch(c -> Utilities.checkParam(c.getKey(), Plato.class)))
            throw new InvalidSearchException();
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.search(criterios, pageable).map(PlatoResponseDTO::of));
    }

    @Operation(summary = "Obtiene todos los platos de un restaurante o busca entre ellos")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado platos",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PagePlatoResponse.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado platos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class)))
    })
    @Parameter(description = "Id del restaurante del cual buscar los platos", name = "id", required = true)
    @JsonView(View.PlatoView.PlatoGenericView.class)
    @GetMapping("/restaurante/{id}")
    public PageDTO<PlatoResponseDTO> findByRestaurant(@RequestParam(value = "search", defaultValue = "")String search,
                                                      @PageableDefault(page = 0, size = 10) Pageable pageable, @PathVariable UUID id){
        List<Criteria> criterios = Utilities.extractCriteria(search);
        criterios.add(new Criteria("restaurante", ":", id));
        if (!criterios.stream().allMatch(c -> Utilities.checkParam(c.getKey(), Plato.class)))
            throw new InvalidSearchException();
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.search(criterios, pageable).map(PlatoResponseDTO::of));
    }

    @Operation(summary = "Obtiene los detalles de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el plato",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PlatoResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato", name = "id", required = true)
    @JsonView(View.PlatoView.PlatoDetailView.class)
    @GetMapping("/{id}")
    public PlatoResponseDTO findById(@PathVariable UUID id){
        return PlatoResponseDTO.of(service.findById(id));
    }

    @Operation(summary = "Obtiene la imagen de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el plato y su imagen",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato del cual buscar la imagen", name = "id", required = true)
    @GetMapping("/{id}/img/")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Plato plato = service.findById(id);
        if(plato.getImgUrl() == null) throw new EntityNotFoundException();
        MediaUrlResource resource =
                (MediaUrlResource) storageService.loadAsResource(plato.getImgUrl());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @Operation(summary = "Cambia la imagen de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha cambiado la imagen",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato del cual cambiar la imagen", name = "id", required = true)
    @PutMapping("/{id}/img/")
    public ResponseEntity<PlatoResponseDTO> changeImage(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(PlatoResponseDTO.of(service.changeImg(loggedUser, id, file)));
    }

    @Operation(summary = "Borra la imagen de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la imagen con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato o su imagen",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato del cual borrar la imagen", name = "id", required = true)
    @DeleteMapping("/{id}/img/")
    public PlatoResponseDTO deleteImg(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        return PlatoResponseDTO.of(service.deleteImg(loggedUser, id));
    }

    @Operation(summary = "Crea un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el plato",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el restaurante al que añadir el plato",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del restaurante",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del restaurante al cual cambiar añadir el plato", name = "restaurantId", required = true)
    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PostMapping("/{restaurantId}")
    public ResponseEntity<PlatoResponseDTO> create(@AuthenticationPrincipal User loggedUser,
                                   @RequestPart("file") MultipartFile file,
                                   @Valid @RequestPart("body") PlatoRequestDTO PlatoDto,
                                   @PathVariable UUID restaurantId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlatoResponseDTO.of(service.add(PlatoDto.toPlato(), restaurantId, loggedUser, file)));
    }

    @Operation(summary = "Edita un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el plato",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato a editar",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato a editar", name = "id", required = true)
    @PutMapping("/{id}")
    public PlatoResponseDTO edit(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody PlatoRequestDTO PlatoDto){
        return PlatoResponseDTO.of(service.edit(id, PlatoDto, loggedUser));
    }

    @Operation(summary = "Borra un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el plato con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el plato",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "403",
                    description = "El usuario no es el propietario del recurso",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        service.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Valora un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha valorado el plato",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos o ya se ha valorado el plato",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato a valorar",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato a valorar", name = "id", required = true)
    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PostMapping("/rate/{id}")
    public ResponseEntity<PlatoResponseDTO> rate(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RateRequestDTO rateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(PlatoResponseDTO.of(service.rate(id, rateDTO, loggedUser)));
    }

    @Operation(summary = "Borra la valoración de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la valoración con éxito",
                    content = {}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el plato o no se ha valorado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato del cual borrar la valoración", name = "id", required = true)
    @JsonView(View.PlatoView.PlatoDetailView.class)
    @DeleteMapping("/rate/{id}")
    public ResponseEntity<?> deleteRating(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        service.deleteRating(id, loggedUser);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita la valoración de un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado la valoración",
                    content = {@Content(schema = @Schema(implementation = PlatoResponseDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el plato o no se ha valorado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "401",
                    description = "No se está loggeado",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
    })
    @Parameter(description = "Id del plato del cual editar la valoración", name = "id", required = true)
    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PutMapping("/rate/{id}")
    public PlatoResponseDTO changeRating(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RateRequestDTO rateDTO){
        return PlatoResponseDTO.of(service.changeRating(id, loggedUser, rateDTO));
    }


}
