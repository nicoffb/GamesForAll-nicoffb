package com.salesianostriana.meal.service;

import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.error.exception.RestaurantInUseException;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.repository.RestauranteRepository;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestauranteFinderService {

    private final RestauranteRepository repository;

    public Restaurante findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->new EntityNotFoundException());
    }

}

