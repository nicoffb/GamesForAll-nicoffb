package com.salesianostriana.gamesforall.category;


import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.ProductRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Obtiene todos las categorias")
    @GetMapping("/list")
    public Set<CategoryDTO> getCategorys( ) {

        Set<CategoryDTO> categorys = categoryService.searchCategorys();

        return categorys;

    }

    @Operation(summary = "Crea una categoria")
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createNewCategory(@RequestBody CategoryDTO created) {


        Category category = categoryService.createCategory(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId()).toUri();

        CategoryDTO converted = CategoryDTO.of(category);


        return ResponseEntity
                .created(createdURI)
                .body(converted);

    }

    @Operation(summary = "Elimina una categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Edita una categoria")
    @PutMapping("/{id}")
    public CategoryDTO editCategory(@PathVariable Long id, @RequestBody CategoryDTO edited) {
        return categoryService.edit(id,edited);
    }


}
