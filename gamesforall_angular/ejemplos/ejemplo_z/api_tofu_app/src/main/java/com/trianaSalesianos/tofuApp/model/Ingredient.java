package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;
    private String name, description;
    @Builder.Default
    private String img = "default_ingredient.jpg";
    @Builder.Default
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    //===============================
    //METODOS PRE-REMOVE Y AUXILIARES
    //===============================
    @PreRemove
    public void setNullRecipes(){
        recipeIngredients.forEach(a -> a.setIngredient(null));
    }

    public void addToAuthor(User u) {
        this.author = u;
        u.getIngredients().add(this);
    }

    public void removeFromAuthor(User u) {
        this.author = null;
        u.getIngredients().remove(this);
    }
}
