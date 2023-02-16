package com.salesianostriana.gamesforall.valoracion.repository;

import com.salesianostriana.gamesforall.valoracion.model.Valoration;
import com.salesianostriana.gamesforall.valoracion.model.ValorationPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorationRepository extends JpaRepository<Valoration, ValorationPK> {
}
