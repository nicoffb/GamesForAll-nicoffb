package com.salesianostriana.meal.service;

import com.salesianostriana.meal.error.exception.AlreadyRatedException;
import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.plato.RateRequestDTO;
import com.salesianostriana.meal.model.key.ValoracionPK;
import com.salesianostriana.meal.repository.PlatoRepository;
import com.salesianostriana.meal.repository.ValoracionRepository;
import com.salesianostriana.meal.search.Criteria;
import com.salesianostriana.meal.search.SpecBuilder;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.service.UserService;
import com.salesianostriana.meal.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public class PlatoService {

    private final PlatoRepository repository;
    private final ValoracionRepository valoracionRepository;
    private final UserService userService;

    private final RestauranteFinderService restauranteFinderService;

    private final StorageService storageService;

    public Plato findById(UUID id) {
        return repository.findFirstById(id).orElseThrow(() ->new EntityNotFoundException());
    }

    public Plato add(Plato plato){
        return repository.save(plato);
    }

    @Transactional
    public Plato add(Plato plato, UUID restaurantId, User loggedUser, MultipartFile file) {
        Restaurante restaurante = restauranteFinderService.findById(restaurantId);
        userService.checkOwnership(restaurante, loggedUser.getId());
        plato.setImgUrl(storageService.store(file));
        plato.addRestaurante(restaurante);
        return repository.save(plato);
    }

    @Transactional
    public void deleteById(UUID id, User loggedUser){
        repository.findById(id).map(p -> {
            userService.checkOwnership(p.getRestaurante(), loggedUser.getId());
            repository.deleteRatings(id);
            repository.deleteById(id);
            return p;
        });
    }

    @Transactional
    public Plato edit(UUID id, PlatoRequestDTO platoRequestDTO, User loggedUser) {
        return repository.findById(id).map(p -> {
            userService.checkOwnership(p.getRestaurante(), loggedUser.getId());
            p.setDescripcion(platoRequestDTO.getDescripcion());
            p.setPrecio(platoRequestDTO.getPrecio());
            p.setNombre(platoRequestDTO.getNombre());
            p.setIngredientes(platoRequestDTO.getIngredientes());
            p.setSinGluten(platoRequestDTO.isSinGluten());
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Page<Plato> search(List<Criteria> criterios, Pageable pageable){
        SpecBuilder<Plato> builder = new SpecBuilder<>(criterios, Plato.class);
        Specification<Plato> spec = builder.build();
        Page<Plato> result = repository.findAll(spec, pageable);
        if(result.getContent().isEmpty()){
            throw new EntityNotFoundException("No se ha encontrado ningún producto");
        }
        return repository.findAll(spec, pageable);
    }

    public Plato rate(UUID id, RateRequestDTO rateRequestDTO, User loggedUser) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isPresent())
            throw new AlreadyRatedException();
        return repository.findFirstById(id).map(p -> {
            ValoracionPK pk = new ValoracionPK(loggedUser.getId(), p.getId());
            Valoracion nueva = Valoracion.builder()
                    .pk(pk)
                    .nota(rateRequestDTO.getNota())
                    .comentario(rateRequestDTO.getComentario())
                    .usuario(loggedUser)
                    .build();
            nueva.addPlato(p);
            valoracionRepository.save(nueva);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(v -> v.getNota()).sum() / p.getValoraciones().size());
            repository.save(p);
            return p;
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    @Transactional
    public Plato deleteRating(UUID id, User loggedUser) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isEmpty())
            throw new EntityNotFoundException();
        Valoracion v = valOpt.get();
        return repository.findFirstById(id).map(p -> {
            p.getValoraciones().remove(v);
            valoracionRepository.deleteById(v.getPk());
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(val -> val.getNota()).sum() / p.getValoraciones().size());
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Plato changeRating(UUID id, User loggedUser, RateRequestDTO rateRequestDTO) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isEmpty())
            throw new EntityNotFoundException();
        Valoracion v = valOpt.get();
        return repository.findFirstById(id).map(p -> {
            v.setComentario(rateRequestDTO.getComentario());
            v.setNota(rateRequestDTO.getNota());
            p.getValoraciones().remove(v);
            p.getValoraciones().add(v);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(val -> val.getNota()).sum() / p.getValoraciones().size());
            valoracionRepository.save(v);
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public boolean hasAnyPlatos(Restaurante restaurante){
        return repository.existsByRestaurante(restaurante);
    }

    public Plato changeImg(User loggedUser, UUID id, MultipartFile file) {
        return repository.findById(id).map(p -> {
            userService.checkOwnership(p.getRestaurante(), loggedUser.getId());
            p.setImgUrl(storageService.store(file));
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Plato deleteImg(User loggedUser, UUID id) {
        return repository.findById(id).map(p -> {
            userService.checkOwnership(p.getRestaurante(), loggedUser.getId());
            storageService.deleteFile(p.getImgUrl());
            p.setImgUrl(null);
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }
}
