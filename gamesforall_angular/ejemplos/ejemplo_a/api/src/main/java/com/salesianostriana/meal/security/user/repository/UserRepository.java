package com.salesianostriana.meal.security.user.repository;

import com.salesianostriana.meal.security.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

    @EntityGraph("owner-con-administraciones")
    Optional<User> findFirstById(UUID uuid);

    @Modifying
    @Query("DELETE FROM Valoracion v WHERE v.usuario.id = :id")
    public void deleteRatings(UUID id);

}

