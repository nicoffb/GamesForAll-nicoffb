package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.exception.ProductNotFoundException;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.search.specifications.PSBuilder;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<EasyProductDTO> findAll(Pageable pageable) {
        Page<EasyProductDTO> pageProductDto = repository.findAll(pageable).map(EasyProductDTO::of);

        if (pageProductDto.isEmpty()) {
            throw new EmptyProductListException();
        }

        return pageProductDto;

    }

    public EasyProductDTO findById(Long id) {
        return repository.findById(id).map(EasyProductDTO::of)
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

    public PageDto<EasyProductDTO> search(List<SearchCriteria> params, Pageable pageable){
        PSBuilder psBuilder = new PSBuilder(params);

        Specification<Product> spec = psBuilder.build();

        Page<EasyProductDTO> pageProductDto = repository.findAll(spec, pageable).map(EasyProductDTO::of);

        return new PageDto<>(pageProductDto);
    }




}
