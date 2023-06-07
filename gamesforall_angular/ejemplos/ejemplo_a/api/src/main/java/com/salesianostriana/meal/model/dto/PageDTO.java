package com.salesianostriana.meal.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.view.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonView({View.RestauranteView.RestauranteDetailView.class, View.RestauranteView.RestauranteGenericView.class,
View.PlatoView.PlatoDetailView.class, View.PlatoView.PlatoGenericView.class, View.VentaView.VentaDetailView.class, View.VentaView.VentaOverView.class})
public class PageDTO<T> {

    private List<T> contenido;
    private long numeroResultados;
    private int numeroPaginas;
    private int paginaActual;

    public PageDTO<T> of(Page page){
        this.contenido = page.getContent();
        this.numeroPaginas = page.getTotalPages();
        this.numeroResultados = page.getTotalElements();
        this.paginaActual = page.getNumber();
        return this;
    }

}
