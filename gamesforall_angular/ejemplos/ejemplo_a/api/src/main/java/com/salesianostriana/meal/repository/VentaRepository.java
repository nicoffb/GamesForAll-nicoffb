package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Venta;
import com.salesianostriana.meal.model.dto.venta.EstadisticasDTO;
import com.salesianostriana.meal.security.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface VentaRepository extends JpaRepository<Venta, UUID> {

    @Query("SELECT DISTINCT v FROM Venta v JOIN LineaVenta lv ON lv.venta = v JOIN Plato p ON lv.plato = p JOIN Restaurante r ON p.restaurante = r WHERE r.restaurantAdmin = :loggedUser ORDER BY v.fecha")
    Page<Venta> findSales(Pageable pageable, User loggedUser);
/*
    @Query("""
        SELECT new com.salesianostriana.meal.model.dto.venta.EstadisticasDTO(SUM(v.totalPedido), COUNT(DISTINCT(v.id))) 
        FROM Venta v 
        JOIN LineaVenta lv ON lv.venta = v 
        WHERE v.fecha > :from 
        AND v.fecha < :to
        AND lv.plato.restaurante.restaurantAdmin = :loggedUser
        """)
    EstadisticasDTO findEstadisticas(LocalDate from, LocalDate to, User loggedUser);*/
}
