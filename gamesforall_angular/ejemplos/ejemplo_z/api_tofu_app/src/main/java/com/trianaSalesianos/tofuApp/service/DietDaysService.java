package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.*;
import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.DietDays;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.dietDay.DietDaysRequest;
import com.trianaSalesianos.tofuApp.model.dto.dietDay.DietDaysResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.repository.CategoryRepository;
import com.trianaSalesianos.tofuApp.repository.DietDaysRepository;
import com.trianaSalesianos.tofuApp.repository.RecipeRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietDaysService {
    private final DietDaysRepository dietDaysRepository;
    private final RecipeRepository recipeRepository;

    public PageDto<DietDaysResponse> search(List<SearchCriteria> params, Pageable pageable) {
        GenericSpecificationBuilder<DietDays> dietSpecificationBuilder = new GenericSpecificationBuilder<>(params, DietDays.class);

        Specification<DietDays> spec = dietSpecificationBuilder.build();
        Page<DietDaysResponse> dietDaysResponsePage = dietDaysRepository.findAll(spec, pageable).map(DietDaysResponse::fromDietDay);

        return new PageDto<>(dietDaysResponsePage);
    }

    public PageDto<DietDaysResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<DietDaysResponse> res = search(params, pageable);

        if (res.getContent().isEmpty()) throw new DietDaysNotFoundException();

        return res;
    }

    public DietDays findById(UUID id) {
        return dietDaysRepository.findById(id)
                .orElseThrow(() -> new DietDaysNotFoundException());
    }

    public DietDays save(DietDays dietDays) {
        return dietDaysRepository.save(dietDays);
    }

    public DietDaysResponse update(DietDaysRequest dietDaysRequest, UUID id) {
        DietDays d = dietDaysRepository.findById(id)
                .orElseThrow(() -> new DietDaysNotFoundException());

        if (!dietDaysRequest.getDay().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dayStr =dietDaysRequest.getDay();
            LocalDate dateTime = LocalDate.parse(dayStr, formatter);

            d.setDay(dateTime);
        }


        return DietDaysResponse.fromDietDay(dietDaysRepository.save(d));
    }


    public void delete(UUID id) {
        DietDays d = findById(id);

        dietDaysRepository.delete(d);
    }

    public DietDaysResponse addRecipeToDiet(UUID idDiet, UUID idRecipe, User user) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());
        DietDays diet = dietDaysRepository.findById(idDiet)
                .orElseThrow(() -> new DietDaysNotFoundException());

        if(!diet.getUser().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if(!diet.getRecipes().stream().anyMatch( r ->
                r.getType().getName().equals
                        (recipe.getType().getName())))
            diet.getRecipes().add(recipe);
        else throw new RecipeTypeInUse();

        dietDaysRepository.save(diet);

        return DietDaysResponse.fromDietDay(diet);
    }

    public void removeRecipeFromDiet(UUID idDiet, UUID idRecipe, User user) {
        DietDays diet = dietDaysRepository.findById(idDiet)
                .orElseThrow(() -> new DietDaysNotFoundException());


        if(!diet.getUser().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if(recipeRepository.existsById(idRecipe))
            diet.getRecipes().removeIf(r -> r.getId().equals(idRecipe));
        else throw new RecipeNotFoundException();

        dietDaysRepository.save(diet);
    }
}
