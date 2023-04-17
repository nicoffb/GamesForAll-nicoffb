package com.salesianostriana.gamesforall.trade.model;

import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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
    @MapsId("seller_id")
    @JoinColumn(name = "seller", foreignKey = @ForeignKey(name = "FK_TRADE_SELLER"),columnDefinition = "uuid")
    private UUID seller;

    @ManyToOne
    @MapsId("buyer_id")
    @JoinColumn(name = "buyer", foreignKey = @ForeignKey(name = "FK_TRADE_BUYER"),columnDefinition = "uuid")
    private UUID buyer;


    //private double price;  podria hacer que el precio fuera el del producto + el del envio


    private double score;

    private String review;


    @Builder.Default
    private LocalDateTime trade_date = LocalDateTime.now();

    private boolean sending;



}

//SEGUN LUISMI LAS TO-MANY OBLIGATORIAMENTE DEBEN SER LAZY