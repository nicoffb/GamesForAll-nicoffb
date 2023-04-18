package com.salesianostriana.gamesforall.shipping.model;

import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.trade.model.Trade;
import com.salesianostriana.gamesforall.user.model.User;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    private String title;
    private String description;

    private String location;

    @Builder.Default
    private LocalDateTime preparation_period = LocalDateTime.now();



    private LocalDateTime sending_date;

    private LocalDateTime delivery_date;



    @ManyToOne //eager
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_USER"))
    private User user;


    @ManyToOne //eager
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

    //HELPERS PLATAFORMAS
//
//    public void addToPlatform(Platform p) {
//        this.platform = p;
//        p.getProductos().add(this);
//    }
//
//    public void removeFromPlatform(Platform p) {
//        this.platform = null;
//        p.getProductos().remove(this);
//    }
//
//
//
//    //HELPERS CATEGORIAS
//    public void addCategory(Category c) {
//        if (this.getCategories() == null)
//            this.setCategories(new HashSet<>());
//        this.getCategories().add(c);
//        if (c.getCategorizedProducts() == null)
//            c.setCategorizedProducts(new ArrayList<>());
//        c.getCategorizedProducts().add(this);
//    }
//
//    public void removeCategory(Category c) {
//        c.getCategorizedProducts().remove(this);
//        this.getCategories().remove(c);
//    }


}

//SEGUN LUISMI LAS TO-MANY OBLIGATORIAMENTE DEBEN SER LAZY