package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM User u JOIN u.followers r WHERE u.id = :followId AND r.id = :userId")
    public boolean isFollowingUser(@Param("followId") UUID followId, @Param("userId") UUID userId);

    boolean existsByName(String name);
}
