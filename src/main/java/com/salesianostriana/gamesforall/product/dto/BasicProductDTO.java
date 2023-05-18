package com.salesianostriana.gamesforall.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.gamesforall.product.model.Category;

import com.salesianostriana.gamesforall.product.model.Product;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Value
public class BasicProductDTO {
//PONER VALIDACIONES PARA EL EDIT

    private Long id;
    private String title;
    private String description;
    private String image;
    private double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime publication_date;
    private String state;
    private boolean shipping_available;
    private boolean sold;
    //private String platform;


    //private Set<Category> category;
   // private Set<String> category;

   // private User user;

    private String userName;
    private String address;
    private double userScore;

    private PlatformDTO platform;

    private Set<CategoryDTO> categories;


    public static BasicProductDTO of(Product product){

        Set<String> genres = new HashSet<>();

        if(product.getCategories()!= null){

        for (Category category : product.getCategories()) {
            genres.add(category.getGenre());
        }
        }

        return BasicProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .publication_date(product.getPublication_date())
                .state(product.getState().getValue())
                .shipping_available(product.isShipping_available())
                .sold(product.isSold())

                //cambiar a entidad
                .platform(product.getPlatform().of)

                .categories(product.getCategories().stream().map(c-> c.of).collect(Collectors.toSet()))

                //dejar categorias sin dto pero con lazy
                //.categories(product.getCategories().)

                //.user(product.getUser()) Da lazy
                .address(product.getUser().getAddress())
                //CUIDADO QUE USERNAME ES NUEVO y NO ESTÁ EN EL FRONT
                .userName(product.getUser().getUsername())
                //esto pide grafo de entidad si sale a partir del createProduct
                //.userScore(product.getUser().getTrades().stream().filter(p -> p.getSeller().equals(product.getUser())).mapToDouble(p -> p.getScore()).average().orElse(0))
                .build();
    }





}
