package com.salesianostriana.gamesforall.product.repository;

import com.salesianostriana.gamesforall.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
