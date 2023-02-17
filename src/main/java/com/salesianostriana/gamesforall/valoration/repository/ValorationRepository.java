package com.salesianostriana.gamesforall.valoration.repository;

import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration, ValorationPK> {
}
