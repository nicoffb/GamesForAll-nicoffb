package com.salesianostriana.gamesforall.product.repository;

import com.salesianostriana.gamesforall.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
