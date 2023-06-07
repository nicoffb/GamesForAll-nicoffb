package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.security.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, UUID> {

    @EntityGraph("restaurante-con-platos")
    @Query("SELECT r FROM Restaurante r WHERE r.id = :id")
    public Optional<Restaurante> findOneWithMenu(UUID id);

    Page<Restaurante> findByRestaurantAdmin(User loggedUser, Pageable pageable);
}
