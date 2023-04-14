package com.salesianostriana.gamesforall.product.model;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Platform {

    @Id
    @GeneratedValue
    private Long id;

    private String platformName;

//    @Builder.Default
//    @OneToMany(mappedBy = "platform", fetch = FetchType.LAZY) //borrado?
//    private List<Product> productos = new ArrayList<>();

}
