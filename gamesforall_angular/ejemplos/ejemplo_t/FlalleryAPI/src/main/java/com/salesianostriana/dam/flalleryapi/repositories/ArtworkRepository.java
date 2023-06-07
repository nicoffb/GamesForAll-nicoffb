package com.salesianostriana.dam.flalleryapi.repositories;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, UUID>, JpaSpecificationExecutor<Artwork> {

    Optional<Artwork> findFirstByName(String name);

    @EntityGraph(value = "Artwork.commentsAndLoved", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Artwork> findByIdArtwork(Long id);

    @Query("""
            SELECT u FROM User u JOIN Loved l on u.username=l.lover WHERE l.lovedArtwork.name = :artworkName
            """)
    List<User> findUsersWhoLikedArtwork(String artworkName);


    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id IN (SELECT co.id FROM Comment co WHERE co.artwork.name = :artworkName)")
    void deleteCommentsOfAnArtwork(String artworkName);



    @Modifying
    @Query("DELETE FROM Loved l WHERE l.id IN (SELECT lo.id FROM Loved lo WHERE lo.lovedArtwork.name = :artworkName)")
    void deleteLovesOfAnArtwork(String artworkName);






}
