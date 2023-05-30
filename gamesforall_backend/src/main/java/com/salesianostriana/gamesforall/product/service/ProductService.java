package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.exception.ProductNotFoundException;
import com.salesianostriana.gamesforall.exception.UserNotFoundException;
import com.salesianostriana.gamesforall.files.service.StorageService;
import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.dto.ProductRequestDTO;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.search.specifications.GSBuilder;
import com.salesianostriana.gamesforall.search.util.Extractor;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final UserRepository userRepository;
    private final StorageService storageService;


    @Transactional
    public Product add(Product product, MultipartFile files) {
        product.setImage(storageService.store(files));
        return repository.save(product);
    }


    public void delete(Product product) {
        repository.delete(product);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }



//COMPLETO
    public PageDto<EasyProductDTO> search(List<SearchCriteria> params, Pageable pageable){
        GSBuilder gsBuilder = new GSBuilder(params,Product.class);

        Specification<Product> spec = gsBuilder.build();

       // Page<EasyProductDTO> pageProductDto = repository.findAll(spec, pageable).map(EasyProductDTO::of);

        //La clase "Specification" de Spring Data permite definir criterios de búsqueda (predicados) de manera programática y combinarlos con operadores lógicos
        //En las especificaciones de Spring Data, "root", "query" y "cb" son parámetros que se utilizan para construir los criterios de búsqueda (predicados) y generar la consulta final.

        Page<EasyProductDTO> pageProductDto = repository.findAll(spec, pageable).map(EasyProductDTO::of);
        //Page<EasyProductDTO> pageProductDto = repository.findAll(Specification.where(spec).and((root, query, cb) -> cb.isFalse(root.get("sold"))), pageable).map(EasyProductDTO::of);

        if (pageProductDto.isEmpty()) {
            throw new EmptyProductListException();
        }

        return new PageDto<>(pageProductDto);
    }

    public BasicProductDTO findById(Long id) {
        return repository.findById(id).map(BasicProductDTO::of)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public BasicProductDTO edit(Long id, ProductRequestDTO productRequestDTO){
        return repository.findById(id)
                .map(product -> {
                    product.setTitle(productRequestDTO.getTitle());
                    product.setPrice(productRequestDTO.getPrice());

                    if (productRequestDTO.getDescription() != null) {
                        product.setDescription(productRequestDTO.getDescription());
                    }
                    product.setShipping_available(productRequestDTO.is_shipping_available());

                    ////
                    if (productRequestDTO.getState() != null) {
                        product.setState(StateEnum.fromString(productRequestDTO.getState()));
                    }
                    if (productRequestDTO.getPlatform() != null) {
                        product.setPlatform(productRequestDTO.getPlatform().toPlatform());
                    }
                    if (productRequestDTO.getCategories() != null) {
                        product.setCategories(productRequestDTO.getCategories().stream().map(c -> c.toCategory()).collect(Collectors.toSet()));
                    }

                    //SOLD OPCIONAL

                    repository.save(product);
                    BasicProductDTO edited = BasicProductDTO.of(product);
                    return edited;
                })
                .orElseThrow(()->new ProductNotFoundException());
    }
    //NO SE YO CREO QUE NO ES LO MEJOR

    @Transactional
    public List<Product> addProductToFavorites(UUID userId, Long idProduct) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()){
            throw new UserNotFoundException(userId);
        }else{
            User found = user.get();
            Optional<Product> product = repository.findById(idProduct);
            if (product.isEmpty()){
                throw new ProductNotFoundException(idProduct);
            }else{
                Product founded2 = product.get();
                found.getFavorites().add(founded2);
                userRepository.save(found);
                return found.getFavorites();
            }

        }

    }

    @Transactional
    public void removeProductFromFavorites(UUID userId, Long idProduct) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(userId);
        } else {
            User found = user.get();
            Optional<Product> product = repository.findById(idProduct);
            if (product.isEmpty()) {
                throw new ProductNotFoundException(idProduct);
            } else {
                Product founded2 = product.get();
                found.getFavorites().remove(founded2);
                userRepository.save(found);
            }
        }
    }






}
