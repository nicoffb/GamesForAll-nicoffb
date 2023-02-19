package com.salesianostriana.gamesforall.valoration.repository;

import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration, ValorationPK>, JpaSpecificationExecutor<Valoration> {

    @Query("SELECT v FROM Valoration v WHERE v.reviewedUser.id = :userId")
    Page<Valoration> findReviewsById(UUID userId,Pageable pageable);

}
