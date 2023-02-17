package com.salesianostriana.gamesforall.product.model;

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
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    //ponerlos uuid

    private String title;
    private String description;

    private String image;

    private double price;


    @Builder.Default
    private LocalDateTime publication_date = LocalDateTime.now();

    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_USER"))
    private User user;



}
