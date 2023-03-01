package com.salesianostriana.gamesforall.valoration.controller;


import com.salesianostriana.gamesforall.product.dto.PageDto;

import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.service.UserService;
import com.salesianostriana.gamesforall.valoration.dto.ValorationDTO;
import com.salesianostriana.gamesforall.valoration.dto.ValorationRequestDTO;
import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import com.salesianostriana.gamesforall.valoration.service.ValorationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/valoration")
@RequiredArgsConstructor
public class ValorationController {

    private final ValorationService valorationService;
    private final UserService userService;


    @Operation(summary = "Obtiene todos las valoraciones de un usuario de forma paginada a partir de su uuid")
    @PageableAsQueryParam
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las valoraciones",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation =ValorationDTO.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.UserNotFoundException.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public PageDto<ValorationDTO> getByCriteria(@PathVariable UUID userId, @PageableDefault(size = 3, page = 0) Pageable pageable) {

        Page<ValorationDTO> valorationList = valorationService.findReviewsById(userId,pageable).map(v->ValorationDTO.of(v));

        return new PageDto<>(valorationList);

    }


    @Operation(summary = "Se crea una valoración del usuario registrado hacia el usuario dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la valoración",
                    content = {@Content(schema = @Schema(implementation = ValorationDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos no son válidos",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el usuario",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.gamesforall.exception.UserNotFoundException.class))),
            @ApiResponse(responseCode = "401",
                    description = "Full authentication is required to access this resource",
                    content = @Content)
    })
    @PostMapping("/{targetUser}")
    public ResponseEntity<ValorationDTO> createValoration (@RequestBody ValorationRequestDTO created, @AuthenticationPrincipal User user, @PathVariable UUID targetUser){

        Valoration valoration = created.toValoration(created);

        ValorationPK pk = new ValorationPK(user.getId(),targetUser);
        valoration.setPK(pk);

        valoration.setUserReview(user);
        valoration.setReviewedUser(userService.findById(targetUser));

        Valoration saved = valorationService.add(valoration);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(valoration.getId()).toUri();

        ValorationDTO converted = ValorationDTO.of(saved);


        return ResponseEntity
                .created(createdURI)
                .body(converted);
    }


}
