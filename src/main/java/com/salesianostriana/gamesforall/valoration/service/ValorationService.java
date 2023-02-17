package com.salesianostriana.gamesforall.valoration.service;


import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import com.salesianostriana.gamesforall.valoration.repository.ValorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    public void deleteById(ValorationPK pk) {repository.deleteById(pk);}

}
