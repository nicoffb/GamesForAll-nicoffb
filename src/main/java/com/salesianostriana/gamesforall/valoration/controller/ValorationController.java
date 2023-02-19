package com.salesianostriana.gamesforall.valoration.controller;


import com.salesianostriana.gamesforall.product.dto.BasicProductDTO;
import com.salesianostriana.gamesforall.product.dto.EasyProductDTO;
import com.salesianostriana.gamesforall.product.dto.PageDto;
import com.salesianostriana.gamesforall.product.dto.ProductRequestDTO;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.search.util.Extractor;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.service.UserService;
import com.salesianostriana.gamesforall.valoration.dto.ValorationDTO;
import com.salesianostriana.gamesforall.valoration.dto.ValorationRequestDTO;
import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import com.salesianostriana.gamesforall.valoration.service.ValorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/valoration")
@RequiredArgsConstructor
public class ValorationController {

    private final ValorationService valorationService;
    private final UserService userService;


    //quiero la puntuación media de los usuarios que le han valorado
    //crear una valoración


    //quiero la lista de valoraciones de un usuario
    @GetMapping("/{userId}")
    public PageDto<ValorationDTO> getByCriteria(@PathVariable UUID userId, @PageableDefault(size = 3, page = 0) Pageable pageable) {

        Page<ValorationDTO> valorationList = valorationService.findReviewsById(userId,pageable).map(v->ValorationDTO.of(v));

        return new PageDto<>(valorationList);

    }


    @PostMapping("/{targetUser}")
    public ResponseEntity<ValorationDTO> createValoration (@RequestBody ValorationRequestDTO created, @AuthenticationPrincipal User user, @PathVariable UUID targetUser){

        Valoration valoration = created.toValoration(created);

        ValorationPK pk = new ValorationPK(user.getId(),targetUser);
        valoration.setPK(pk);

        valoration.setUserReview(user);
        valoration.setReviewedUser(userService.findById(targetUser));

        Valoration saved = valorationService.add(valoration);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(valoration.getId()).toUri();

        ValorationDTO converted = ValorationDTO.of(saved);


        return ResponseEntity
                .created(createdURI)
                .body(converted);
        //gestionar el fallo con bad request o manejo de errores

        //cuando añado una valoracion se actualiza el valor del atributo media del usuario y lo guardas
    }


}
