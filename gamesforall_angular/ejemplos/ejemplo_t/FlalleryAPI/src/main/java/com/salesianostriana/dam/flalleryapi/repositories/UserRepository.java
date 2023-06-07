package com.salesianostriana.dam.flalleryapi.repositories;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    @Query("""
            SELECT l.lovedArtwork FROM Loved l WHERE l.lover = :userName
            """)
    List<Artwork> findArtworksLikedByUser(String userName);

    @Query("""
            SELECT a FROM Artwork a WHERE a.owner = :userName
            """)
    List<Artwork> findArtworksOfAUser(String userName);

    @Query("""
            SELECT c FROM Comment c WHERE c.writer = :userName
            """)
    List<Comment> findAllCommentsOfAUser(String userName);

    @Modifying
    @Query("delete from Artwork a where a.owner=:owner")
    void deleteArtworksOfAUSer(String owner);

    @Modifying
    @Query("delete from Comment c where c.writer=:owner")
    void deleteCommentsOfAUSer(String owner);
}
