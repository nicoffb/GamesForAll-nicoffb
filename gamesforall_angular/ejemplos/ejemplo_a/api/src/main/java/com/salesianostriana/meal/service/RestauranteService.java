package com.salesianostriana.meal.service;

import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.error.exception.RestaurantInUseException;
import com.salesianostriana.meal.model.Cocina;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.repository.RestauranteRepository;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.service.UserService;
import com.salesianostriana.meal.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;
    private final PlatoService platoService;
    private final UserService userService;
    private final StorageService storageService;

    private final CocinaService cocinaService;

    public Page<Restaurante> findAll(Pageable pageable){
        Page<Restaurante> result = repository.findAll(pageable);
        if(result.getNumberOfElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Restaurante add(RestauranteRequestDTO restauranteDTO, User loggedUser, MultipartFile file){
        Restaurante nuevoRestaurante = restauranteDTO.toRestaurante();
        nuevoRestaurante.setCocina(new ArrayList<Cocina>());
        restauranteDTO.getCocinas().forEach(c -> {
            nuevoRestaurante.getCocina().add(cocinaService.findById(c));
        });
        nuevoRestaurante.setRestaurantAdmin(loggedUser);
        nuevoRestaurante.setCoverImgUrl(storageService.store(file));
        return repository.save(nuevoRestaurante);
    }

    @Transactional
    public void deleteById(UUID id, final  User loggedUser){
        repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            if(platoService.hasAnyPlatos(r)){
                throw new RestaurantInUseException();
            }
            repository.deleteById(id);
            return r;
        });
    }

    @Transactional
    public Restaurante edit(UUID id, RestauranteRequestDTO restauranteRequestDTO, final User loggedUser){
        return repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            r.setApertura(restauranteRequestDTO.getApertura());
            r.setCierre(restauranteRequestDTO.getCierre());
            r.setNombre(restauranteRequestDTO.getNombre());
            r.setDescripcion(restauranteRequestDTO.getDescripcion());
            return repository.save(r);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Restaurante findWithMenu(UUID id) {
        return repository.findOneWithMenu(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Restaurante changeImg(User loggedUser, UUID id, MultipartFile file) {
        return repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            r.setCoverImgUrl(storageService.store(file));
            return repository.save(r);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Restaurante deleteImg(User loggedUser, UUID id) {
        return repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            storageService.deleteFile(r.getCoverImgUrl());
            return repository.save(r);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Page<Restaurante> findManaged(User loggedUser, Pageable pageable) {
        return repository.findByRestaurantAdmin(loggedUser, pageable);
    }
}
