package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.exception.ProductNotFoundException;
import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
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


    public PageDto<EasyProductDTO> search(List<SearchCriteria> params, Pageable pageable){
        PSBuilder psBuilder = new PSBuilder(params);

        Specification<Product> spec = psBuilder.build();

        Page<EasyProductDTO> pageProductDto = repository.findAll(spec, pageable).map(EasyProductDTO::of);

        if (pageProductDto.isEmpty()) {
            throw new EmptyProductListException();
        }

        return new PageDto<>(pageProductDto);
    }

    public EasyProductDTO findById(Long id) {
        return repository.findById(id).map(EasyProductDTO::of)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public BasicProductDTO edit(Long id, BasicProductDTO editBasicProductDTO){
        return repository.findById(id)
                .map(product -> {
                    product.setTitle(editBasicProductDTO.getTitle());
                    product.setDescription(editBasicProductDTO.getDescription());
                    product.setImage(editBasicProductDTO.getImage());
                    product.setPrice(editBasicProductDTO.getPrice());
                    product.setCategory(editBasicProductDTO.getCategory());
                    repository.save(product);
                    BasicProductDTO edited = BasicProductDTO.of(product);
                    return edited;
                })
                .orElseThrow(()->new ProductNotFoundException());
    }










}
