package com.salesianostriana.gamesforall.shipping.repository;

import com.salesianostriana.gamesforall.shipping.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {
}
