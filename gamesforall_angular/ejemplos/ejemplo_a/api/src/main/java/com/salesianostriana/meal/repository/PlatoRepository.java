package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, UUID>, JpaSpecificationExecutor<Plato> {

    @Query("SELECT p FROM Plato p WHERE p.restaurante.id = :id")
    public Page<Plato> findByRestaurant(UUID id, Pageable pageable);

    @EntityGraph(value = "plato-con-valoraciones", type = EntityGraph.EntityGraphType.LOAD)
    public Optional<Plato> findFirstById(UUID id);

    @Modifying
    @Query("DELETE FROM Valoracion v WHERE v.plato.id = :id")
    public void deleteRatings(UUID id);

    public boolean existsByRestaurante(Restaurante restaurante);

}
