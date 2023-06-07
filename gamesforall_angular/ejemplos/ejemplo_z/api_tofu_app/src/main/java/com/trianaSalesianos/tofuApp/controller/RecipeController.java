package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.exception.UserNotFoundException;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.Type;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientAmountRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeDetailsResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserLikesResponse;
import com.trianaSalesianos.tofuApp.service.RecipeService;
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

@Tag(name = "Recipes", description = "Recipes controller")
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")

public class RecipeController {
    final private RecipeService recipeService;

    @Operation(summary = "Get all the recipes, can use search parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All recipes fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                                 "content": [
                                                                     {
                                                                         "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                         "name": "Licensed Soft Pizza",
                                                                         "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                         "category": "Vegan",
                                                                         "img": "default_recipe.jpg",
                                                                         "author": "Alejandro Damas",
                                                                         "prepTime": 20,
                                                                         "createdAt": "17/02/2023 00:41:49"
                                                                     },
                                                                     {
                                                                         "id": "ac132001-865c-1a80-8186-5c9b147c0002",
                                                                         "name": "Rustic Fresh Chair",
                                                                         "description": "bypassing AI Customer Tuna",
                                                                         "category": "Standard",
                                                                         "img": "default_recipe.jpg",
                                                                         "author": "Alejandro Damas",
                                                                         "prepTime": 30,
                                                                         "createdAt": "17/02/2023 00:41:52"
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
                    description = "Recipe Not found",
                    content = @Content),
    })
    @GetMapping("")
    public PageDto<RecipeResponse> getAll(
            @Parameter(description = "Can be used to search recipes by their variables")
            @RequestParam(value = "search", defaultValue = "") String search,
                                          @PageableDefault(size = 10, page = 0) Pageable pageable){
        return recipeService.getAllBySearch(search, pageable);
    }

    @GetMapping("/author/{username}")
    public PageDto<RecipeResponse>getByUsername(
            @PathVariable String username,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return recipeService.getRecipesByUsername(username, pageable);
    }

    @GetMapping("/likes/{username}")
    public PageDto<RecipeResponse>getLikesByUsername(
            @PathVariable String username,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return recipeService.getLikesByUsername(username, pageable);
    }
    @Operation(summary = "Get an recipe by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recipe fetched succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RecipeResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Licensed Soft Pizza",
                                                    "description": "systems Legacy Cambridgeshire platforms ROI",
                                                    "category": "Vegan",
                                                    "img": "default_recipe.jpg",
                                                    "author": "Alejandro Damas",
                                                    "steps": "You can't index the sensor without copying the neural THX panel!",
                                                    "prepTime": 20,
                                                    "favorites": 0,
                                                    "ingredients": [],
                                                    "createdAt": "17/02/2023 00:41:49"
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Recipe not found",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public RecipeDetailsResponse getById(
            @Parameter(description = "Id of the ingredient to get")
            @PathVariable UUID id){
        return RecipeDetailsResponse.fromRecipe(recipeService.findById(id));
    }

    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Recipe created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac111001-8680-12d2-8186-803a4a3d0000",
                                                    "name": "Unbranded Frozen Bike",
                                                    "description": "PCI index Bacon Car systems",
                                                    "category": "Vegan",
                                                    "img": "default_recipe.jpg",
                                                    "author": "Alejandro Damas",
                                                    "prepTime": 30,
                                                    "createdAt": "23/02/2023 22:42:29"
                                                }                                                                   
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("")
    public ResponseEntity<RecipeResponse> create(
            @AuthenticationPrincipal User loggedUser,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values required to create a recipe")
            @Valid @RequestBody RecipeRequest recipeRequest
    ){

        Recipe created = recipeService.createRecipe(recipeRequest, loggedUser);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(RecipeResponse.fromRecipe(recipeService.save(created)));
    }

    @Operation(summary = "Edit the image of a recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recipe edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865c-1a80-8186-5c9afcbe0000",
                                                    "name": "Intelligent Fresh Mouse",
                                                    "description": "Tools monetize",
                                                    "category": "Vegetarian",
                                                    "img": "peepoClown.jpg",
                                                    "author": "Alejandro Damas",
                                                    "prepTime": 50,
                                                    "createdAt": "17/02/2023 00:41:46"
                                                }                                                                
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Recipe not found",
                    content = @Content),

    })
    @PutMapping("/img/{id}")
    public RecipeResponse changeImg(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Image to upload")
            @RequestPart("file") MultipartFile file,
                                    @Parameter(description = "Id of the recipe to edit")
                                    @PathVariable UUID id,
                                    @AuthenticationPrincipal User user
    ){
        return recipeService.changeImg(file,id, user);
    }

    @Operation(summary = "Edit the values of a recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recipe edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "ac132001-865c-1a80-8186-5c9afcbe0000",
                                                    "name": "Generic Wooden Computer",
                                                    "description": "Avon Tennessee applications Card XML",
                                                    "category": "Vegetariano",
                                                    "img": "peepoClown.jpg",
                                                    "author": "Alejandro Damas",
                                                    "prepTime": 50,
                                                    "createdAt": "17/02/2023 00:41:46"
                                                }                                                              
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Recipe not found",
                    content = @Content),

    })
    @PutMapping("/{id}")
    public RecipeResponse update(
            @Parameter(description = "Id of the recipe to edit")
            @PathVariable UUID id,
                                 @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values to update")
                                 @Valid @RequestBody RecipeRequest recipeRequest,
                                 @AuthenticationPrincipal User user){
        return recipeService.update(recipeRequest, id, user);
    }


    @Operation(summary = "Adding an ingredient to a recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Ingredient added succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeDetailsResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Licensed Soft Pizza",
                                                    "description": "systems Legacy Cambridgeshire platforms ROI",
                                                    "category": "Vegan",
                                                    "img": "default_recipe.jpg",
                                                    "author": "Alejandro Damas",
                                                    "steps": "You can't index the sensor without copying the neural THX panel!",
                                                    "prepTime": 20,
                                                    "favorites": 0,
                                                    "ingredients": [
                                                        {
                                                            "recipe": {
                                                                "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                "name": "Licensed Soft Pizza",
                                                                "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                "category": "Vegan",
                                                                "img": "default_recipe.jpg",
                                                                "author": "Alejandro Damas",
                                                                "prepTime": 20,
                                                                "createdAt": "17/02/2023 00:41:49"
                                                            },
                                                            "ingredient": {
                                                                "name": "Bacon",
                                                                "img": "default_ingredient.jpg"
                                                            },
                                                            "amount": 250.0,
                                                            "unit": "grams"
                                                        }
                                                    ],
                                                    "createdAt": "17/02/2023 00:41:49"
                                                }                                                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/{id_recipe}/ingredient/{id_ingredient}")
    public RecipeIngredientResponse addIngredientToRecipe(
            @AuthenticationPrincipal User user,
            @Parameter(description = "Id of the recipe")
            @PathVariable UUID id_recipe,
            @Parameter(description = "Id of the ingredient to be added")
            @PathVariable UUID id_ingredient,
            @RequestBody IngredientAmountRequest recipeIngredientRequest
            ){
        return recipeService.addIngredient(id_recipe,id_ingredient,recipeIngredientRequest,user);
    }

    @Operation(summary = "Create a new ingredient in a recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Ingredient created succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeDetailsResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Licensed Soft Pizza",
                                                    "description": "systems Legacy Cambridgeshire platforms ROI",
                                                    "category": "Vegan",
                                                    "img": "default_recipe.jpg",
                                                    "author": "Alejandro Damas",
                                                    "steps": "You can't index the sensor without copying the neural THX panel!",
                                                    "prepTime": 20,
                                                    "favorites": 0,
                                                    "ingredients": [
                                                        {
                                                            "recipe": {
                                                                "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                "name": "Licensed Soft Pizza",
                                                                "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                "category": "Vegan",
                                                                "img": "default_recipe.jpg",
                                                                "author": "Alejandro Damas",
                                                                "prepTime": 20,
                                                                "createdAt": "17/02/2023 00:41:49"
                                                            },
                                                            "ingredient": {
                                                                "name": "Bacon",
                                                                "img": "default_ingredient.jpg"
                                                            },
                                                            "amount": 250.0,
                                                            "unit": "grams"
                                                        },
                                                        {
                                                            "recipe": {
                                                                "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                "name": "Licensed Soft Pizza",
                                                                "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                "category": "Vegan",
                                                                "img": "default_recipe.jpg",
                                                                "author": "Alejandro Damas",
                                                                "prepTime": 20,
                                                                "createdAt": "17/02/2023 00:41:49"
                                                            },
                                                            "ingredient": {
                                                                "name": null,
                                                                "img": "default_ingredient.jpg"
                                                            },
                                                            "amount": 2.0,
                                                            "unit": "pieces"
                                                        }
                                                    ],
                                                    "createdAt": "17/02/2023 00:41:49"
                                                }                                                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/{id_recipe}/ingredient/")
    public RecipeIngredientResponse createIngredientInRecipe(
            @AuthenticationPrincipal User user,
            @Parameter(description = "Id of the recipe")
            @PathVariable UUID id_recipe,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to create the ingredient")
            @RequestBody RecipeIngredientRequest ingredient
    ){
        return recipeService.createIngredientInRecipe(id_recipe,ingredient, user);
    }

    @Operation(summary = "Like a recipe with the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Like added succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserLikesResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "username": "ADamas",
                                                    "favorites": [
                                                        {
                                                            "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                            "name": "Licensed Soft Pizza",
                                                            "description": "systems Legacy Cambridgeshire platforms ROI",
                                                            "category": "Vegan",
                                                            "img": "default_recipe.jpg",
                                                            "author": "Alejandro Damas",
                                                            "prepTime": 20,
                                                            "createdAt": "17/02/2023 00:41:49"
                                                        }
                                                    ]
                                                }                                                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
    })
    @PostMapping("/{id}/like")
    public UserLikesResponse likeRecipe(
            @Parameter(description = "Id of the recipe to like")
            @PathVariable UUID id,
            @AuthenticationPrincipal User user){

        return recipeService.likeRecipe(id, user);
    }

    @Operation(summary = "Update the amount of an ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ingredient amount edited succesfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeDetailsResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "name": "Licensed Soft Pizza",
                                                    "description": "systems Legacy Cambridgeshire platforms ROI",
                                                    "category": "Vegan",
                                                    "img": "default_recipe.jpg",
                                                    "author": "Alejandro Damas",
                                                    "steps": "You can't index the sensor without copying the neural THX panel!",
                                                    "prepTime": 20,
                                                    "favorites": 1,
                                                    "ingredients": [
                                                        {
                                                            "recipe": {
                                                                "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                "name": "Licensed Soft Pizza",
                                                                "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                "category": "Vegan",
                                                                "img": "default_recipe.jpg",
                                                                "author": "Alejandro Damas",
                                                                "prepTime": 20,
                                                                "createdAt": "17/02/2023 00:41:49"
                                                            },
                                                            "ingredient": {
                                                                "name": "Bacon",
                                                                "img": "default_ingredient.jpg"
                                                            },
                                                            "amount": 250.0,
                                                            "unit": "grams"
                                                        },
                                                        {
                                                            "recipe": {
                                                                "id": "ac132001-865c-1a80-8186-5c9b088d0001",
                                                                "name": "Licensed Soft Pizza",
                                                                "description": "systems Legacy Cambridgeshire platforms ROI",
                                                                "category": "Vegan",
                                                                "img": "default_recipe.jpg",
                                                                "author": "Alejandro Damas",
                                                                "prepTime": 20,
                                                                "createdAt": "17/02/2023 00:41:49"
                                                            },
                                                            "ingredient": {
                                                                "name": null,
                                                                "img": "default_ingredient.jpg"
                                                            },
                                                            "amount": 2.0,
                                                            "unit": "pieces"
                                                        }
                                                    ],
                                                    "createdAt": "17/02/2023 00:41:49"
                                                }                                                                       
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "There was an error with the data provided",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Ingredient or Recipe not found",
                    content = @Content),

    })
    @PutMapping("/{id_recipe}/ingredient/{id_ingredient}/changeamount")
    public RecipeDetailsResponse updateAmount(
            @AuthenticationPrincipal User user,
            @Parameter(description = "Id of the recipe")
            @PathVariable UUID id_recipe,
            @Parameter(description = "Id of the ingredient")
            @PathVariable UUID id_ingredient,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Values needed to update the amount")
            @RequestBody IngredientAmountRequest recipeIngredientRequest
    ){
        return recipeService.updateAmount(id_recipe, id_ingredient, recipeIngredientRequest, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeRecipe(@PathVariable UUID id, @AuthenticationPrincipal User user){

        recipeService.deleteById(id, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/{id_recipe}/category/{id_category}")
    public RecipeResponse addCategoryToRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_category
    ){
        return recipeService.addCategoryToRecipe(user, id_category,id_recipe);
    }

    @PutMapping("/{id_recipe}/type/{id_type}")
    public RecipeResponse changeType(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_type
            ){
        return recipeService.changeType(user, id_type, id_recipe);
    }

    @DeleteMapping("/{id_recipe}/ingredient/{id_ingredient}")
    public RecipeDetailsResponse removeIngredientFromRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_ingredient
    ){

        return recipeService.removeIngredient(user, id_ingredient,id_recipe);
    }

    @DeleteMapping("/{id_recipe}/category/{id_category}")
    public RecipeResponse removeCategoryFromRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_category
    ){
        return recipeService.removeCategoryFromRecipe(user, id_category, id_recipe);
    }

    @DeleteMapping("/{id_recipe}/step/{id_step}")
    public RecipeDetailsResponse removeStepFromRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_step
    ){
        return recipeService.removeStepFromRecipe(user,id_step,id_recipe);
    }
    @PostMapping("/{id_recipe}/step/{id_step}")
    public RecipeDetailsResponse addStepToRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id_recipe,
            @PathVariable UUID id_step
    ){
        return recipeService.addStepToRecipe(user,id_step,id_recipe);
    }
}
