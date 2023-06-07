package com.salesianostriana.dam.flalleryapi.repositories;

import com.salesianostriana.dam.flalleryapi.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VentaRepository extends JpaRepository<Venta, Long>, JpaSpecificationExecutor<Venta> {

}
