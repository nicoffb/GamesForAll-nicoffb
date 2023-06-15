package com.salesianostriana.gamesforall.product.model;

import com.salesianostriana.gamesforall.category.Category;
import com.salesianostriana.gamesforall.platform.Platform;
import com.salesianostriana.gamesforall.trade.model.Trade;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


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

    private String title;
    private String description;

    private String image;

    private double price;


    @Builder.Default
    private LocalDateTime publication_date = LocalDateTime.now();


    private StateEnum state;

    private boolean shipping_available;

    private boolean sold;


    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_USER"))
    private User user;


    @ManyToOne
    @JoinColumn(name = "platform_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_PLATAFORM"))
    private Platform platform;


    @ManyToMany//borrado?(cascade = CascadeType.ALL) nunca borrar
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_PRODUCT")),
            inverseJoinColumns = @JoinColumn(name = "category_id",foreignKey = @ForeignKey(name = "FK_CATEGORY")))
    private Set<Category> categories;



    @OneToOne(mappedBy = "product")
    private Trade trade;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

//SEGUN LUISMI LAS TO-MANY OBLIGATORIAMENTE DEBEN SER LAZY