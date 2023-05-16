package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Builder
@Value
public class ProductRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private double price;

    @NotBlank
    private String category;

    //ANOTACION PERSONALIZADA
    private String state;


    private Long idPlatform;

  //  private Set<Category> categoria;


    public Product toProduct(ProductRequestDTO productRequestDTO){


      // Platform platform = plat.find(Platform.class, productRequestDTO.getIdPlatform());


        return Product.builder()
                .title(productRequestDTO.getTitle())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .state(StateEnum.fromString(productRequestDTO.getState()))
               // .platform(productRequestDTO.get)
                .build();
    }

}
