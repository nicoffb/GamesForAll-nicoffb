package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.Venta;
import com.salesianostriana.meal.repository.ValoracionRepository;
import com.salesianostriana.meal.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    private final ValoracionRepository repository;

    public Page<Valoracion> findsLast(User loggedUser, Pageable pageable){
        Page<Valoracion> result = repository.findLast(pageable, loggedUser);
        if(result.getNumberOfElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

}
