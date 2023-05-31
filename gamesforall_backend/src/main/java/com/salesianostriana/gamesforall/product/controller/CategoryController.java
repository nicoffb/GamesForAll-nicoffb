package com.salesianostriana.gamesforall.product.controller;


import com.salesianostriana.gamesforall.product.dto.CategoryDTO;

import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.service.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Set<CategoryDTO> getCategorys( ) {

        Set<CategoryDTO> categorys = categoryService.searchCategorys();

        return categorys;

    }

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


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }


}
