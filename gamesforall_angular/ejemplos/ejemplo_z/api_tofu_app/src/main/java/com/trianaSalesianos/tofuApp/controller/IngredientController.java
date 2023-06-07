package com.trianaSalesianos.tofuApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Tag(name = "Ingredients", description = "Ingredients controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
//@CrossOrigin(origins = "http://localhost:4200")

public class IngredientController {
    final private IngredientService ingredientService;

    @Operation(summary = "Get all the ingredients, can use search parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All ingredients fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "content": [
                                                        {
                                                            "name": "Salad",
                                                            "img": "default_ingredient.jpg"
                                                        },
                                                        {
                                                            "name": "Chicken",
                                                            "img": "default_ingredient.jpg"
                                                        },
                                                        {
                                                            "name": "Bacon",
                                                            "img": "default_ingredient.jpg"
                                                        },
                                                        {
                                                            "name": "Tuna",
                                                            "img": "default_ingredient.jpg"
                                                        },
                                                        {
                                                            "name": "Cheese",
                                                            "img": "default_ingredient.jpg"
                                                        }
                                                    ],
                                                    "last": true,
                                                    "first": true,
                                                    "totalPages": 1,
                                                    "totalElements": 5
                                                }            
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Ingredient Not found",
                    content = @Content),
    })
    @GetMapping("")
    public PageDto<IngredientResponse> getAll(
            @Parameter(description = "Can be used to search ingredients by their variables")
            @RequestParam(value = "search", defaultValue = "") String search,
                                              @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ingredientService.getAllBySearch(search, pageable);
    }

    @GetMapping("/recipe/{recipeId}")
    public PageDto<IngredientResponse> getIngredientsOfRecipe(
            @PathVariable UUID recipeId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return ingredientService.getIngredientsOfRecipe(recipeId, pageable);
    }
    @GetMapping("/author/{username}")
    public PageDto<IngredientResponse> getIngredientsByAuthor(
            @PathVariable String username,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return ingredientService.getIngredientsByAuthor(username,pageable);
    }

    @Operation(summary = "Get an ingredient by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ingredient fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IngredientResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Chicken",
                                                    "img": "default_ingredient.jpg"
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Ingredient not found",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public IngredientResponse getById(
            @Parameter(description = "Id of the ingredient to get")
            @PathVariable UUID id) {
        return IngredientResponse.fromIngredient(ingredientService.findById(id));
    }


    @Operation(summary = "Create a new ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Ingredient created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Cheese",
                                                    "img": "http://placeimg.com/640/480/food"
                                                }                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<IngredientResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to create an ingredient")
            @Valid @RequestBody IngredientRequest ingredientRequest,
            @AuthenticationPrincipal User user
    ) {
        Ingredient created = Ingredient.builder()
                .name(ingredientRequest.getName())
                .description(ingredientRequest.getDescription())
                .author(user)
                .build();

        ingredientService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(IngredientResponse.fromIngredient(created));
    }

    @Operation(summary = "Edit the image of an ingredient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ingredient edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "img": "koalaBored.jpg"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Ingredient not found",
                    content = @Content),

    })
    @PutMapping("/img/{id}")
    public IngredientResponse changeImg(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Image to upload")
            @RequestPart("file") MultipartFile file,
                                        @Parameter(description = "Id of the ingredient to edit")
                                        @PathVariable UUID id){
        return ingredientService.changeImg(file,id);
    }


    @Operation(summary = "Edit the name of an ingredient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ingredient edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IngredientResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Shirt"
                                                }                                                                  
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Ingredient not found",
                    content = @Content),

    })
    @PutMapping("/{id}")
    public IngredientResponse update(
            @Parameter(description = "Id of the ingredient to edit")
            @PathVariable UUID id,
                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New name for the ingredient")
                                     @Valid @RequestBody IngredientRequest ingredientRequest){
        return ingredientService.update(ingredientRequest,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeIngredient(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ){

        ingredientService.delete(id, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
