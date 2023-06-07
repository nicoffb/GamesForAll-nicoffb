package com.salesianostriana.dam.flalleryapi.controllers;


import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import com.salesianostriana.dam.flalleryapi.models.User;
import com.salesianostriana.dam.flalleryapi.models.UserRole;
import com.salesianostriana.dam.flalleryapi.models.dtos.artwork.ArtworkCreateRequest;
import com.salesianostriana.dam.flalleryapi.models.dtos.artwork.ArtworkResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.artworkcategory.ArtworkCategoryCreateRequest;
import com.salesianostriana.dam.flalleryapi.models.dtos.artworkcategory.ArtworkCategoryResponse;
import com.salesianostriana.dam.flalleryapi.models.dtos.user.UserResponse;
import com.salesianostriana.dam.flalleryapi.services.ArtworkCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
public class ArtworkCategoryController {


    private final ArtworkCategoryService artworkCategoryService;

    @Operation(summary = "Get a list of all Artwork Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Categories Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ArtworkCategory.class)),
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
                    description = "No Categories Found",
                    content = @Content),
    })
    @GetMapping("/category/")
    public ResponseEntity<List<ArtworkCategoryResponse>> findAll() {

        List<ArtworkCategory> artworkCategoryList = artworkCategoryService.findAll();

        if (artworkCategoryList.isEmpty())
            return ResponseEntity.notFound().build();

        List<ArtworkCategoryResponse> responseList = artworkCategoryList.stream().map(ArtworkCategoryResponse::artworkCategoryToArtworkCategoryResponse).toList();
        return ResponseEntity.ok(responseList);
    }


    @Operation(summary = "Get a single Artwork Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Artwork Category Found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ArtworkCategory.class)),
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
                    description = "No Artwork Category Found",
                    content = @Content),
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<ArtworkCategoryResponse> getArtworkCategory(@PathVariable Long id){

        Optional<ArtworkCategory> artworkCategory = artworkCategoryService.findById(id);

        return artworkCategory.map(category -> ResponseEntity.of(Optional.of(ArtworkCategoryResponse.artworkCategoryToArtworkCategoryResponse(category))))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PutMapping("/category/{name}")
    public ResponseEntity<ArtworkCategoryResponse> editArtworkCategory(
            @RequestBody ArtworkCategoryCreateRequest artworkCategoryCreateRequest,
            @PathVariable String name){
        Optional<ArtworkCategory> artworkCategoryResponse = artworkCategoryService.edit(name, artworkCategoryCreateRequest);

        return artworkCategoryResponse
                .map(cat -> ResponseEntity.status(HttpStatus.OK)
                .body(ArtworkCategoryResponse.artworkCategoryToArtworkCategoryResponse(cat)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create a new Artwork Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Category Created Successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ArtworkCategory.class)),
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
                    description = "Bad Artwork Creation Request",
                    content = @Content),
    })
    @PostMapping("/category/")
    public ResponseEntity<ArtworkCategoryResponse>createArtworkCategory(
            @RequestBody ArtworkCategoryCreateRequest artworkCategoryCreateRequest,
            @AuthenticationPrincipal User user){

        if (user.getRoles().contains(UserRole.ADMIN)) {
            ArtworkCategory artworkCategory = artworkCategoryService
                    .save(artworkCategoryCreateRequest.artworkCategoryCreateRequestToArtworkCategory());

            URI createdURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/category/")
                    .buildAndExpand(artworkCategory.getIdCategory()).toUri();

            return ResponseEntity
                    .created(createdURI)
                    .body(ArtworkCategoryResponse.artworkCategoryToArtworkCategoryResponse(artworkCategory));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Operation(summary = "Delete an Artwork Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artwork.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No Artwork found",
                    content = @Content),
    })
    @DeleteMapping("/category/{name}")
    public ResponseEntity<?> deleteArtworkCategory(
            @PathVariable String name,
            @AuthenticationPrincipal User user) {

        if (user.getRoles().contains(UserRole.ADMIN)) {
        Optional<ArtworkCategory> artworkCategory = artworkCategoryService.findByName(name);
        if (artworkCategory.isPresent()){
            artworkCategoryService.delete(artworkCategory.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
