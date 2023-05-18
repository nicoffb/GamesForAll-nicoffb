package com.salesianostriana.gamesforall.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.trade.model.Trade;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String platform;
    private Set<String> category;
    private String address;
    private double userScore;
    //posibilidad de cargar un dto de usuario dentro

    public static BasicProductDTO of(Product product){

//        String shippingStatus = product.isShipping_available() ? "Envío disponible" : "Sin envío";
//
//        String soldStatus = product.isSold() ? "Vendido" : "Sin vender";

        //double userScore = getUserScoreSum(product.getUser());

        Set<String> genres = new HashSet<>();
        for (Category category : product.getCategories()) {
            genres.add(category.getGenre());
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
                .platform(product.getPlatform().getPlatformName())
                .category(genres)
                .address(product.getUser().getAddress())
                .userScore(product.getUser().getTrades().stream().filter(p -> p.getSeller().equals(product.getUser())).mapToDouble(p -> p.getScore()).average().orElse(0))
                .build();
    }

    private static double getUserScoreSum(User user) {
        double sum = 0.0;
        for (Trade trade : user.getTrades()) {
            if (trade.getSeller().equals(user)) {
                sum += trade.getScore();
            }
        }
        return sum;
    }



}
