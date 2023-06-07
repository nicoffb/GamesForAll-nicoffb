package com.salesianostriana.dam.flalleryapi.controllers;


import com.salesianostriana.dam.flalleryapi.models.*;
import com.salesianostriana.dam.flalleryapi.models.Venta;
import com.salesianostriana.dam.flalleryapi.models.dtos.PageDto;
import com.salesianostriana.dam.flalleryapi.models.dtos.venta.VentaCreateRequest;
import com.salesianostriana.dam.flalleryapi.models.dtos.venta.VentaResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.venta.VentaResponse;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteria;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteriaExtractor;
import com.salesianostriana.dam.flalleryapi.services.ArtworkService;
import com.salesianostriana.dam.flalleryapi.services.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final ArtworkService artworkService;

    @Operation(summary = "Get a list of all Ventas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ventas Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Venta.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "content": [
                                                         {
                                                             "name": "Guerra Romana en témperas",
                                                             "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                             "comments": [],
                                                             "owner": "Titin",
                                                             "description": "Guerra de los años 90 hecha en témperas"
                                                         }
                                                     ],
                                                     "totalPages": 1,
                                                     "totalElements": 1,
                                                     "pageSize": 1
                                                 }
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Ventas Found",
                    content = @Content),
    })
    @GetMapping("/venta")
    public ResponseEntity<PageDto<VentaResponse>> search(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "s", defaultValue = "") String s,
            @PageableDefault(size = 25, page = 0) Pageable pageable) {

        if (user.getRoles().contains(UserRole.ADMIN)){
            List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(s);


            Page<Venta> result = ventaService.search(params, pageable);
            Page<VentaResponse> response = result.map(VentaResponse::ventaToVentaResponse);

            if (result.isEmpty())
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok(new PageDto<VentaResponse>(response));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


    @Operation(summary = "Get a single Venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Venta Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Venta.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "name": "Guerra Romana en témperas",
                                                     "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                     "comments": [],
                                                     "owner": "Titin",
                                                     "description": "Guerra de los años 90 hecha en témperas"
                                                 }
                                            ]                                        \s
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Venta Found",
                    content = @Content),
    })
    @GetMapping("/venta/{id}")
    public ResponseEntity<VentaResponse> getVenta(@AuthenticationPrincipal User user, @PathVariable Long id){

        if (user.getRoles().contains(UserRole.ADMIN)){
            Optional<Venta> venta = ventaService.findVentaByID(id);
            if (venta.isPresent()){
                VentaResponse response = new VentaResponse();
                return ResponseEntity.of(Optional.of(VentaResponse.ventaToVentaResponse(venta.get())));
            }

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


    @Operation(summary = "Create a new Venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Venta Created Successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Venta.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "name": "Guerra Romana en témperas",
                                                     "uuid": "c0a8002c-8659-1f7a-8186-5a0082f10001",
                                                     "comments": [],
                                                     "owner": "Titin",
                                                     "description": "Guerra de los años 90 hecha en témperas"
                                                 }
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Venta Creation Request",
                    content = @Content),
    })
    @PostMapping("artwork/{id}/venta/")
    public ResponseEntity<VentaResponse>createVenta(
            @RequestBody VentaCreateRequest ventaCreateRequest,
            @PathVariable UUID id,
            @AuthenticationPrincipal User user){

        Optional<Artwork> artwork = artworkService.findById(id);

        if (artwork.isPresent()) {
            Venta venta = ventaService.add(ventaCreateRequest.ventaCreateRequestToVenta(artwork.get()));


            URI createdURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/venta/{id}")
                    .buildAndExpand(venta.getIdVenta()).toUri();

            return ResponseEntity
                    .created(createdURI)
                    .body(VentaResponse.ventaToVentaResponse(venta));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/venta/{id}")
    public ResponseEntity<?> deleteVenta(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        if (user.getRoles().contains(UserRole.ADMIN)){
            Optional<Venta> venta = ventaService.findVentaByID(id);
            if (venta.isPresent() & venta.get().getArtwork()!=null){
                ventaService.deleteVenta(venta.get());
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
