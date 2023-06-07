package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.CategoryNotFoundException;
import com.trianaSalesianos.tofuApp.exception.IngredientNotFoundException;
import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.repository.CategoryRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public PageDto<CategoryResponse> search(List<SearchCriteria> params, Pageable pageable){
        GenericSpecificationBuilder<Category> catSpecificationBuilder = new GenericSpecificationBuilder<>(params, Category.class);

        Specification<Category> spec = catSpecificationBuilder.build();
        Page<CategoryResponse> categoryResponsePage = categoryRepository.findAll(spec,pageable).map(CategoryResponse::fromCategory);

        return new PageDto<>(categoryResponsePage);
    }
    public PageDto<CategoryResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<CategoryResponse> res = search(params,pageable);

        if (res.getContent().isEmpty()) throw new CategoryNotFoundException();

        return res;
    }

    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponse update(CategoryRequest categoryRequest, UUID id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());

        if(!categoryRequest.getName().isEmpty())
            c.setName(categoryRequest.getName());

        if(!categoryRequest.getColor().isEmpty())
            c.setColorCode(categoryRequest.getColor());

        return CategoryResponse.fromCategory(categoryRepository.save(c));
    }


    public void delete(UUID id) {
        Category c = findById(id);

        categoryRepository.delete(c);
    }

    public boolean categoryExists(String s) {
        return categoryRepository.existsByName(s);
    }
}
