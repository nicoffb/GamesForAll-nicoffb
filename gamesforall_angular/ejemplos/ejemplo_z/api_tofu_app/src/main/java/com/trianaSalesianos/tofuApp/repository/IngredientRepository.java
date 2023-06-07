package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Ingredient;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.ingredient.IngredientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.nio.channels.FileChannel;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, JpaSpecificationExecutor<Ingredient> {
    Page<Ingredient> findByAuthor(User user, Pageable pageable);
}
