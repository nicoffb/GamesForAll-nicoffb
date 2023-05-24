package com.salesianostriana.gamesforall.product.repository;

import com.salesianostriana.gamesforall.product.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform,Long> {
}
