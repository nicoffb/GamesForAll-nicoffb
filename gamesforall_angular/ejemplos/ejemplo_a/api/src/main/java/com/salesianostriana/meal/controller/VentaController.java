package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.model.dto.venta.EstadisticasDTO;
import com.salesianostriana.meal.model.dto.venta.VentaResponseDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService service;

    @GetMapping("/")
    @JsonView(View.VentaView.VentaOverView.class)
    public PageDTO<VentaResponseDTO> getLastSales (@AuthenticationPrincipal User loggedUser,
                                                                   @PageableDefault(page = 0, size = 10) Pageable pageable) {
        PageDTO<VentaResponseDTO> result = new PageDTO<>();
        return result.of(service.findSales(loggedUser, pageable).map(VentaResponseDTO::of));
    }
/*
    @GetMapping("/recaudacion")
    public EstadisticasDTO getRecaudacion (@AuthenticationPrincipal User loggedUser, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        return service.getEstadisticas(from, to, loggedUser);
    }*/
}
