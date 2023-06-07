package com.salesianostriana.meal.controller;

import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.RateResponseDTO;
import com.salesianostriana.meal.model.dto.venta.VentaResponseDTO;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.ValoracionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valoraciones")
@RequiredArgsConstructor
public class ValoracionController {

    private final ValoracionService service;

    @GetMapping("/")
    public PageDTO<RateResponseDTO> getLastValoraciones(@AuthenticationPrincipal User loggedUser,
                                                        @PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<RateResponseDTO> result = new PageDTO<>();
        return result.of(service.findsLast(loggedUser, pageable).map(RateResponseDTO::of));
    }

}
