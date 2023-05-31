package com.salesianostriana.gamesforall.product.repository;

import com.salesianostriana.gamesforall.category.Category;
import com.salesianostriana.gamesforall.platform.Platform;
import com.salesianostriana.gamesforall.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

//    //@EntityGraph("product-with-platform-and-categories")
//    Optional<Product> findById(Long id);

    List<Product> findByPlatform(Platform platform);

    List<Product> findByCategoriesContains(Category category);
}
