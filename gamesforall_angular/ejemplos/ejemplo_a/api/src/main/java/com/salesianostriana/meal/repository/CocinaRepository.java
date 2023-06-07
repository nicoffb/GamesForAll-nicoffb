package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Cocina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CocinaRepository extends JpaRepository<Cocina, UUID> {
}
