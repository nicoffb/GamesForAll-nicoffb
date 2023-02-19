package com.salesianostriana.gamesforall.product.service;

import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.exception.ProductNotFoundException;
import com.salesianostriana.gamesforall.exception.UserNotFoundException;
import com.salesianostriana.gamesforall.files.service.StorageService;
import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.model.Product;
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

//NUEVOS
    public Page<EasyProductDTO> findAll(Pageable pageable) {
        Page<EasyProductDTO> pageProductDto = repository.findAll(pageable).map(EasyProductDTO::of);

        if (pageProductDto.isEmpty()) {
            throw new EmptyProductListException();
        }

        return pageProductDto;

    }

//COMPLETO
    public PageDto<EasyProductDTO> search(List<SearchCriteria> params, Pageable pageable){
        GSBuilder gsBuilder = new GSBuilder(params,Product.class);

        Specification<Product> spec = gsBuilder.build();

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


    public List<Product> addProductToFavorites(UUID userId, Long idProduct) {
        // Buscamos el usuario y el producto

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

        //comprobar si están presentes con un if is present?
        //.orElseThrow(() -> UserIdNotFoundException) CREAR EXCEPCION SI NO SE ENCUENTRA AL USUARIO

//        Product product = repository.findById(idProduct)
//                .orElseThrow(() -> new ProductNotFoundException(idProduct));

        // Agregamos el producto a la lista de favoritos del usuario


    }





}
