package com.salesianostriana.gamesforall.product.model;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String genre;

//    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
//    private List<Product> categorizedProducts;


    public Category(String genre) {
        this.genre = genre;
    }
}
