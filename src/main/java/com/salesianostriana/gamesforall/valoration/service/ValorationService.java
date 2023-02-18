package com.salesianostriana.gamesforall.valoration.service;


import com.salesianostriana.gamesforall.exception.EmptyProductListException;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.search.specifications.PSBuilder;
import com.salesianostriana.gamesforall.search.specifications.VABuilder;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.service.UserService;
import com.salesianostriana.gamesforall.valoration.dto.ValorationDTO;
import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import com.salesianostriana.gamesforall.valoration.repository.ValorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValorationService {


    private final ValorationRepository repository;

    public Valoration add(Valoration valoration) {
        return repository.save(valoration);
    }


    public void delete(Valoration valoration) {
        repository.delete(valoration);
    }

//    public void deleteById(Long id) {
//        repository.deleteById(id); //no va porque validation no tiene id, sino clave compuesta
//    }

    public PageDto<ValorationDTO> findAll(List<SearchCriteria> params, Pageable pageable, UUID userId){
        VABuilder vaBuilder = new VABuilder(params);

        Specification<Valoration> spec = vaBuilder.build(); //no he hecho un ValorationSpecification, solo un VABuilder

        //aqui devolvemos las valoraciones del id elegido pero sin criteria
                                                                //aqui se cogen todas hayh que cambiarlo
        Page<Valoration> pageValoration = repository.findAllByReviewedUserId(userId, spec, pageable);

        if (pageValoration.isEmpty()) {
            throw new EmptyProductListException();  //cambiar la excepcion a valoracion empty
        }

        Page<ValorationDTO> pageValorationDto = pageValoration.map(ValorationDTO::of);

        return new PageDto<>(pageValorationDto);
    }



}
