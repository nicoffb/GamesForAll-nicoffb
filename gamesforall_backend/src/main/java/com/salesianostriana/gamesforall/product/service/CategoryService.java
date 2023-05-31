package com.salesianostriana.gamesforall.product.service;


import com.salesianostriana.gamesforall.exception.CategoryNotFoundException;
import com.salesianostriana.gamesforall.exception.EmptyCategoryListException;
import com.salesianostriana.gamesforall.product.dto.CategoryDTO;
import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.CategoryRepository;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public Set<CategoryDTO> searchCategorys(){

        Set<CategoryDTO> categoryDTOS = categoryRepository.findAll().stream()
                .map(CategoryDTO::of)
                .collect(Collectors.toSet());

        if (categoryDTOS.isEmpty()) {
            throw new EmptyCategoryListException();
        }

        return categoryDTOS;
    }



    public Category createCategory(CategoryDTO categoryDTO){

        Category category = categoryDTO.toCategory();
        categoryRepository.save(category);
        return category;
    }


    public void deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException());
        List<Product> products = productRepository.findByCategoriesContains(category);
        for (Product product : products) {
            product.getCategories().remove(category);
            productRepository.save(product);
        }
        categoryRepository.delete(category);
    }



}
