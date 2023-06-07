package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Recipe;
import com.trianaSalesianos.tofuApp.model.User;
import com.trianaSalesianos.tofuApp.model.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM User u JOIN u.followers r WHERE u.id = :followId AND r.id = :userId")
    public boolean isFollowingUser(@Param("followId") UUID followId, @Param("userId") UUID userId);

    @Query("SELECT u.favorites FROM User u Where u.username =:username")
    Page<Recipe> findFavoritesByUser(@Param("username") String username, Pageable pageable);

}
