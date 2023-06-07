package com.trianaSalesianos.tofuApp.controller;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.service.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Tag(name= "Category", description = "Categories controllers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
//@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public PageDto<CategoryResponse> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        return categoryService.getAllBySearch(search, pageable);
    }
    @GetMapping("/{id}")

    public CategoryResponse getById(
            @Parameter(description = "Id of the category to get")
            @PathVariable UUID id) {
        return CategoryResponse.fromCategory(categoryService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<CategoryResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data required to create a category")
            @Valid @RequestBody CategoryRequest categoryRequest,
            @AuthenticationPrincipal User user
    ) {
        Category created = Category.builder()
                .name(categoryRequest.getName())
                .colorCode(categoryRequest.getColor())
                .build();

        categoryService.save(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(CategoryResponse.fromCategory(created));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(
            @Parameter(description = "Id of the category to edit")
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New name for the category")
            @Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.update(categoryRequest,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCategory(
            @PathVariable UUID id
    ){

        categoryService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
