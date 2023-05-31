package com.salesianostriana.gamesforall.shipping.model;

import com.salesianostriana.gamesforall.trade.model.Trade;
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
public class Shipping {

    @Id
    @GeneratedValue
    private Long id;

    private String location;

    @Builder.Default
    private String preparation_period = LocalDateTime.now() + " --- " + LocalDateTime.now().plusDays(2);

    private LocalDateTime sending_date;

    private LocalDateTime delivery_date;


    @OneToOne(mappedBy = "shipping")
    private Trade trade;



}

