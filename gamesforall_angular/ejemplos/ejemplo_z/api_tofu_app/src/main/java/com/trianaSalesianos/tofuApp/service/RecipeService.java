package com.trianaSalesianos.tofuApp.service;


import com.trianaSalesianos.tofuApp.exception.*;
import com.trianaSalesianos.tofuApp.model.*;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientAmountRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientRequest;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.RecipeIngredientResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeDetailsResponse;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeRequest;
import com.trianaSalesianos.tofuApp.model.dto.recipe.RecipeResponse;
import com.trianaSalesianos.tofuApp.model.dto.user.UserLikesResponse;
import com.trianaSalesianos.tofuApp.repository.*;
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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final StorageService storageService;
    private final UserRepository userRepository;
    private final TypeRepository typeRepository;
    private final CategoryRepository categoryRepository;
    private final StepRepository stepRepository;


    public PageDto<RecipeResponse> search(List<SearchCriteria> params, Pageable pageable) {
        GenericSpecificationBuilder<Recipe> recipeSpecificationBuilder = new GenericSpecificationBuilder<>(params, Recipe.class);

        Specification<Recipe> spec = recipeSpecificationBuilder.build();
        Page<RecipeResponse> recipeResponsePage = recipeRepository.findAll(spec, pageable).map(RecipeResponse::fromRecipe);

        return new PageDto<>(recipeResponsePage);
    }

    public PageDto<RecipeResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<RecipeResponse> res = search(params, pageable);

        if (res.getContent().isEmpty()) throw new RecipeNotFoundException();

        return res;
    }

    public Recipe findById(UUID id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Transactional
    public RecipeResponse changeImg(MultipartFile file, UUID id, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        String filename = storageService.store(file);

        recipe.setImg(filename);

        return RecipeResponse.fromRecipe(recipeRepository.save(recipe));
    }

    public RecipeResponse update(RecipeRequest recipeRequest, UUID id, User user) {
        Recipe rec = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipeRequest.getName().isEmpty())
            rec.setName(recipeRequest.getName());

        if (!recipeRequest.getDescription().isEmpty())
            rec.setDescription(recipeRequest.getDescription());

        if (!recipeRequest.getPrepTime().equals(0)) {
            rec.setPrepTime(recipeRequest.getPrepTime());
        }

        if (!recipeRequest.getType().equals(0)) {
            rec.setType(Type.builder().name(recipeRequest.getType()).build());
        }

        return RecipeResponse.fromRecipe(recipeRepository.save(rec));
    }


    public RecipeIngredientResponse addIngredient(UUID id_recipe,
                                                  UUID id_ingredient,
                                                  IngredientAmountRequest recipeIngredientRequest,
                                                  User user) {

        Recipe recipe = recipeRepository.findById(id_recipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Ingredient ingredient = ingredientRepository.findById(id_ingredient)
                .orElseThrow(() -> new IngredientNotFoundException());


        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();
        RecipeIngredient ri;

        if (!recipeIngredientRepository.existsById(new RecipeIngredientPK(id_recipe, id_ingredient))) {
            ri = RecipeIngredient.builder()
                    .unit(recipeIngredientRequest.getUnit())
                    .amount(recipeIngredientRequest.getAmount())
                    .recipe(recipe)
                    .ingredient(ingredient)
                    .build();

        } else {
            ri = recipeIngredientRepository.findById(new RecipeIngredientPK(id_recipe, id_ingredient)).get();

            double amount = ri.getAmount();

            ri.setAmount(amount + recipeIngredientRequest.getAmount());
        }
        recipeIngredientRepository.save(ri);

        return RecipeIngredientResponse.fromRecipeIngredient(ri);
    }

    public UserLikesResponse likeRecipe(UUID id, User user) {
        User userAuthenticated = userRepository.findFirstByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException());
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (recipeRepository.isFavoritedByUser(userAuthenticated.getId(), id)) {
            userAuthenticated.getFavorites().removeIf(f -> f.getId().equals(recipe.getId()));
            recipe.getFavoritedBy().removeIf(u -> u.getId().equals(userAuthenticated.getId()));
        } else {
            userAuthenticated.getFavorites().add(recipe);
            recipe.getFavoritedBy().add(userAuthenticated);
        }

        userRepository.save(userAuthenticated);
        recipeRepository.save(recipe);

        return UserLikesResponse.fromUser(userAuthenticated);
    }

    public RecipeDetailsResponse updateAmount(UUID id_recipe, UUID id_ingredient, IngredientAmountRequest recipeIngredientRequest, User user) {
        Recipe recipe = recipeRepository.findById(id_recipe)
                .orElseThrow(() -> new RecipeNotFoundException());
        Ingredient ingredient = ingredientRepository.findById(id_ingredient)
                .orElseThrow(() -> new IngredientNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if (!recipe.getRecipeIngredients()
                .stream()
                .anyMatch(i -> i.getIngredient().getId().equals(id_ingredient)))
            throw new IngredientNotFoundException();

        recipeRepository.updateAmount(
                recipeIngredientRequest.getUnit(),
                recipeIngredientRequest.getAmount(),
                ingredient, recipe);

        recipeRepository.save(recipe);
        ingredientRepository.save(ingredient);

        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public RecipeIngredientResponse createIngredientInRecipe(UUID id, RecipeIngredientRequest ingredient, User user) {
        User userAuthenticated = userRepository.findFirstByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException());
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipe.getAuthor().getId().equals(userAuthenticated.getId()))
            throw new RecipeAuthorNotValidException();

        Ingredient ing = Ingredient.builder()
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .author(userAuthenticated)
                .build();

        RecipeIngredient ri = RecipeIngredient.builder()
                .ingredient(ing)
                .recipe(recipe)
                .amount(ingredient.getAmount())
                .unit(ingredient.getUnit())
                .build();

        ingredientRepository.save(ing);
        recipeIngredientRepository.save(ri);
        recipeRepository.save(recipe);

        return RecipeIngredientResponse.fromRecipeIngredient(ri);
    }


    public void deleteById(UUID id, User user) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        recipeRepository.deleteById(id);
    }

    public RecipeResponse addCategoryToRecipe(User user, UUID idCategory, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new CategoryNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        //OTra opcion seria probar con anymatch
        boolean hasCategory = recipe.getCategories()
                .stream()
                .filter(c -> c.getId().equals(idCategory))
                .toList().size() > 0;

        if (!hasCategory) {
            recipe.getCategories().add(category);
        } else {
            recipe.getCategories().remove(category);
        }
        recipeRepository.save(recipe);


        return RecipeResponse.fromRecipe(recipe);
    }


    public RecipeResponse changeType(User user, UUID idType, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Type type = typeRepository.findById(idType)
                .orElseThrow(() -> new TypeNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        recipe.setType(type);
        recipeRepository.save(recipe);

        return RecipeResponse.fromRecipe(recipe);
    }

    public RecipeDetailsResponse removeIngredient(User user, UUID idIngredient, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        Ingredient ingredient = ingredientRepository.findById(idIngredient)
                .orElseThrow(() -> new IngredientNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        RecipeIngredientPK idRI = new RecipeIngredientPK(idRecipe, idIngredient);

        if (recipeIngredientRepository.existsById(idRI)) {
            RecipeIngredient ri = recipeIngredientRepository.findById(idRI)
                    .orElseThrow(() -> new EntityNotFoundException());

            recipe.getRecipeIngredients().removeIf(r -> r.getId().equals(ri.getId()));
            ingredient.getRecipeIngredients().removeIf(r -> r.getId().equals(ri.getId()));

            recipeIngredientRepository.deleteById(idRI);

            recipeRepository.save(recipe);
            ingredientRepository.save(ingredient);
        } else throw new IngredientNotFoundInRecipeException();

        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public RecipeResponse removeCategoryFromRecipe(User user, UUID idCategory, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());


        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if (categoryRepository.existsById(idCategory))
            recipe.getCategories().removeIf(c -> c.getId().equals(idCategory));
        else throw new CategoryNotFoundException();

        recipeRepository.save(recipe);

        return RecipeResponse.fromRecipe(recipe);
    }

    public RecipeDetailsResponse removeStepFromRecipe(User user, UUID idStep, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        if (stepRepository.existsById(idStep)) {
            recipe.getSteps().removeIf(s -> s.getId().equals(idStep));
            recipe.getSteps().forEach(s -> s.setStepNumber(recipe.getSteps().indexOf(s) + 1));
        } else throw new StepNotFoundException();

        recipeRepository.save(recipe);
        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public RecipeDetailsResponse addStepToRecipe(User user, UUID idStep, UUID idRecipe) {
        Recipe recipe = recipeRepository.findById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException());
        Step step = stepRepository.findById(idStep)
                .orElseThrow(() -> new StepNotFoundException());

        if (!recipe.getAuthor().getId().equals(user.getId()))
            throw new RecipeAuthorNotValidException();

        recipe.getSteps().add(step);
        recipe.getSteps().forEach(s -> s.setStepNumber(recipe.getSteps().indexOf(s) + 1));

        recipeRepository.save(recipe);
        stepRepository.save(step);
        return RecipeDetailsResponse.fromRecipe(recipe);
    }

    public Recipe createRecipe(RecipeRequest recipeRequest, User loggedUser) {
        Type type = typeRepository.findFirstByName(recipeRequest.getType())
                .orElseThrow(() -> new TypeNotFoundException());

        return Recipe.builder()
                .name(recipeRequest.getName())
                .description(recipeRequest.getDescription())
                .author(loggedUser)
                .prepTime(recipeRequest.getPrepTime())
                .type(type)
                .build();
    }

    public PageDto<RecipeResponse> getRecipesByUsername(String username, Pageable pageable) {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());
        Page<RecipeResponse> recipeResponsePage = recipeRepository.findByAuthor(user, pageable).map(RecipeResponse::fromRecipe);
        return new PageDto<>(recipeResponsePage);
    }

    public PageDto<RecipeResponse> getLikesByUsername(String username, Pageable pageable) {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Page<RecipeResponse> likeResponsePage = userRepository.findFavoritesByUser(username, pageable).map(RecipeResponse::fromRecipe);
        return new PageDto<>(likeResponsePage);
    }
}
