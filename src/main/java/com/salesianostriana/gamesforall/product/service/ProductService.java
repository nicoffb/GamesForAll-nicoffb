package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.exception.ProductNotFoundException;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public Product add(Product product) {
        return repository.save(product);
    }


    public void delete(Product product) {
        repository.delete(product);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

//NUEVOS
    public List<Product> findAll() {
        List<Product> result = repository.findAll();

        if (result.isEmpty()) {
            throw new EmptyProductListException();
        }

        return result;

    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Optional<Product> findById2(Long id) {
        return repository.findById(id);
    }
    //no usar preferiblemente


    public Product edit(Long id,Product edited){
        return repository.findById(id)
                .map(product -> {
                    product.setTitle(edited.getTitle());
                    product.setDescription(edited.getDescription());
                    return repository.save(product);
                })
                .orElseThrow(()->new ProductNotFoundException());
    }




}
