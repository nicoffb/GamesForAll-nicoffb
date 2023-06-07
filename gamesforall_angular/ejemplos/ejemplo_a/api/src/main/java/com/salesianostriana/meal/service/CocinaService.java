package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Cocina;
import com.salesianostriana.meal.repository.CocinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CocinaService {

    private final CocinaRepository repository;

    public List<Cocina> findAll(){
        List<Cocina> result = repository.findAll();
        if (result.isEmpty()) throw new EntityNotFoundException();
        return result;
    }

    public Cocina findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

}
