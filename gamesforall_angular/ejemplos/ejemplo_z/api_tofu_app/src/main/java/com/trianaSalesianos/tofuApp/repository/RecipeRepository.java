package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM User u JOIN u.favorites r WHERE u.id = :userId AND r.id = :recipeId")
    public boolean isFavoritedByUser(@Param("userId") UUID userId, @Param("recipeId") UUID recipeId);

    @Modifying
    @Transactional
    @Query("UPDATE RecipeIngredient r SET r.amount = :amount, r.unit = :unit WHERE r.ingredient = :ingredient AND r.recipe = :recipe")
    void updateAmount(@Param("unit") String unit, @Param("amount") double amount, @Param("ingredient") Ingredient ingredient, @Param("recipe") Recipe recipe);


    Page<Recipe> findByAuthor(User author, Pageable pageable);

    @Query("SELECT ri.ingredient FROM RecipeIngredient ri WHERE ri.recipe.id = :recipeId")
    Page<Ingredient> findIngredientsOfRecipe(@Param("recipeId") UUID recipeId, Pageable pageable);

    @Query("SELECT r.steps FROM Recipe r Where r.id =:idRecipe")
    List<Step> findFavoritesByUser(@Param("idRecipe") UUID idRecipe);
}
