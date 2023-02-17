package com.salesianostriana.gamesforall.valoration.controller;


import com.salesianostriana.gamesforall.valoration.service.ValorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valoration")
@RequiredArgsConstructor
public class ValorationController {

    private final ValorationService valorationService;

    //quiero la lista de valoraciones de un usuario
    //quiero la puntuación media de los usuarios que le han valorado



}
