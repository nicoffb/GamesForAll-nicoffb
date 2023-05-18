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

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private double price;


    //ANOTACION PERSONALIZADA
    private String state;


    private PlatformDTO platform;

    private Set<CategoryDTO> categories;

    private UserResponse user;



    public Product toProduct(ProductRequestDTO productRequestDTO){

          User user = new User();
          if(this.user != null){
              user=this.user.toUser();
          }

            return Product.builder()
                    .title(productRequestDTO.getTitle())
                    .description(productRequestDTO.getDescription())
                    .price(productRequestDTO.getPrice())
                    .state(StateEnum.fromString(productRequestDTO.getState()))
                    .platform(productRequestDTO.getPlatform().toPlatform())
                    .categories(productRequestDTO.getCategories().stream().map(c -> c.toCategory()).collect(Collectors.toSet()))
                    .user(user)
                    .build();


    }

}
