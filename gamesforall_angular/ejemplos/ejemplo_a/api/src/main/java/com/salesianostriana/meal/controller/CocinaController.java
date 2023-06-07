package com.salesianostriana.meal.controller;

import com.salesianostriana.meal.model.Cocina;
import com.salesianostriana.meal.service.CocinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocina")
@RequiredArgsConstructor
public class CocinaController {

    private final CocinaService service;

    @Operation(summary = "Obtiene todos los tipos de cocina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado tipos de cocina",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Cocina.class)))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado tipos de cocina",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class))),
            @ApiResponse(responseCode = "400",
                    description = "La búsqueda es incorrecta",
                    content = @Content(schema = @Schema(implementation = com.salesianostriana.meal.error.model.Error.class)))
    })
    @GetMapping("/")
    public List<Cocina> findAll(){
        return service.findAll();
    }

}
