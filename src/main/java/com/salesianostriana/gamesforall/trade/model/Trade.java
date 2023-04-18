package com.salesianostriana.gamesforall.trade.model;

import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;



@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", foreignKey = @ForeignKey(name = "FK_TRADE_SELLER"),columnDefinition = "uuid")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", foreignKey = @ForeignKey(name = "FK_TRADE_BUYER"),columnDefinition = "uuid")
    private User buyer;


    //private double price;  podria hacer que el precio fuera el del producto + el del envio


    private double score;

    private String review;


    @Builder.Default
    private LocalDateTime trade_date = LocalDateTime.now();

    private boolean sending;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;


}

//SEGUN LUISMI LAS TO-MANY OBLIGATORIAMENTE DEBEN SER LAZY