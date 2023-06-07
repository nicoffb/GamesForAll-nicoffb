package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.key.ValoracionPK;
import com.salesianostriana.meal.security.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ValoracionRepository extends JpaRepository<Valoracion, ValoracionPK> {

    @Query("SELECT v FROM Valoracion v WHERE v.plato.restaurante.restaurantAdmin = :loggedUser ORDER BY v.fecha")
    Page<Valoracion> findLast(Pageable pageable, User loggedUser);
}
