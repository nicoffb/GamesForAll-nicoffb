package com.salesianostriana.gamesforall.product.dto;

import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.user.dto.UserResponse;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Value
@Setter
public class ProductRequestDTO {

    @NotBlank
    private String title;

    //@NotBlank
    private String description;

    @NotNull
    @Positive
    private double price;




    //ANOTACION PERSONALIZADA
    private String state;


    private PlatformDTO platform;

    private Set<CategoryDTO> categories;

    private boolean is_shipping_available;
    //private UserResponse user;



    public Product toProduct(ProductRequestDTO productRequestDTO){
            return Product.builder()
                    .title(productRequestDTO.getTitle())
                    .description(productRequestDTO.getDescription())
                    .price(productRequestDTO.getPrice())
                    .state(StateEnum.fromString(productRequestDTO.getState()))
                    .platform(productRequestDTO.getPlatform().toPlatform())
                    .categories(productRequestDTO.getCategories().stream().map(c -> c.toCategory()).collect(Collectors.toSet()))
                    .shipping_available(productRequestDTO.is_shipping_available())
                   // .user(user)
                    .build();
    }

    public static ProductRequestDTO of(Product product) {
        return ProductRequestDTO.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                //.image(product.getImage())
                .price(product.getPrice())
                .state(product.getState().getValue())
                .is_shipping_available(product.isShipping_available())
                //.sold(product.isSold())
                .platform(PlatformDTO.of(product.getPlatform()))
                .categories(product.getCategories().stream()
                        .map(CategoryDTO::of)
                        .collect(Collectors.toSet())).
                build();
    }

}
