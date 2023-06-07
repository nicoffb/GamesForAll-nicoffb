package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.*;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import com.trianaSalesianos.tofuApp.repository.IngredientRepository;
import com.trianaSalesianos.tofuApp.repository.RecipeRepository;
import com.trianaSalesianos.tofuApp.repository.UserRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import com.trianaSalesianos.tofuApp.service.files.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService {
    final private IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;


    private final StorageService storageService;

    public PageDto<IngredientResponse> search(List<SearchCriteria> params, Pageable pageable){
        GenericSpecificationBuilder<Ingredient> ingSpecificationBuilder = new GenericSpecificationBuilder<>(params, Ingredient.class);

        Specification<Ingredient> spec = ingSpecificationBuilder.build();
        Page<IngredientResponse> ingredientResponsePage = ingredientRepository.findAll(spec,pageable).map(IngredientResponse::fromIngredient);

        return new PageDto<>(ingredientResponsePage);
    }
    public PageDto<IngredientResponse> getAllBySearch(String search, Pageable pageable){
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<IngredientResponse> res = search(params,pageable);

        if (res.getContent().isEmpty()) throw new IngredientNotFoundException();

        return res;
    }

    public Ingredient save(Ingredient ing){
        return ingredientRepository.save(ing);
    }

    public Ingredient findById(UUID id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException());
    }

    @Transactional
    public IngredientResponse changeImg(MultipartFile file, UUID id) {
        String filename = storageService.store(file);
        Ingredient ing = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException());

        ing.setImg(filename);

        return IngredientResponse.fromIngredient(ingredientRepository.save(ing));
    }

    public IngredientResponse update(IngredientRequest ingredientRequest, UUID id) {
        Ingredient ing = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException());

        if(!ingredientRequest.getName().isEmpty())
            ing.setName(ingredientRequest.getName());

        if(!ingredientRequest.getDescription().isEmpty())
            ing.setDescription(ingredientRequest.getDescription());

        return IngredientResponse.fromIngredient(ingredientRepository.save(ing));
    }

    public void delete(UUID id, User user) {
        Ingredient ing = findById(id);

        if (!ing.getAuthor().getId().equals(user.getId()))
            throw new IngredientAuthorNotValidException();

        ingredientRepository.delete(ing);
    }

    public PageDto<IngredientResponse> getIngredientsByAuthor(String username, Pageable pageable) {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new RecipeAuthorNotValidException());

        Page<IngredientResponse> ingredientResponsePage = ingredientRepository.findByAuthor(user, pageable).map(IngredientResponse::fromIngredient);

        return new PageDto<>(ingredientResponsePage);
    }

    public PageDto<IngredientResponse> getIngredientsOfRecipe(UUID recipeId, Pageable pageable) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException());
        Page<IngredientResponse> ingredientResponsePage = recipeRepository.findIngredientsOfRecipe(recipeId, pageable).map(IngredientResponse::fromIngredient);

        return new PageDto<>(ingredientResponsePage);
    }
}
