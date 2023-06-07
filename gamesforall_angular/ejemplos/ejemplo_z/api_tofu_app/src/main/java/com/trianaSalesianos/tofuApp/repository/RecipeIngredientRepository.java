package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.RecipeIngredient;
import com.trianaSalesianos.tofuApp.model.RecipeIngredientPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.TypedQuery;
import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientPK>, JpaSpecificationExecutor<RecipeIngredient> {
}
