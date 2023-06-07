package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Recipe implements Serializable {
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
    private String name;
    private String description;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_id")
    private Type type;

    @Builder.Default
    private Integer prepTime = 0;   // in minutes

    @OneToMany(mappedBy ="recipe",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();
    @Builder.Default
    private String img = "default_recipe.jpg";
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name="FK_CATEGORY_RECIPE")),
            inverseJoinColumns = @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name="FK_RECIPE_CATEGORY"))
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();  //Vegetariano, vegano, hiper-proteico, hiper-calorico, hipo-calorico...
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_RECIPE_AUTHOR"))
    private User author;
    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "favorites", cascade = CascadeType.PERSIST)
    private List<User> favoritedBy = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    //===============================
    //METODOS PRE-REMOVE Y AUXILIARES
    //===============================

    @PreRemove
    public void removeFavorite(){
        favoritedBy.forEach(this::removeFavorite);
    }

    public void favorite(User u) {
        this.favoritedBy.add(u);
        u.getFavorites().add(this);
    }

    public void removeFavorite(User u) {
        this.favoritedBy.remove(u);
        u.getFavorites().remove(this);
    }

    public void addToAuthor(User u) {
        this.author = u;
        u.getRecipes().add(this);
    }

    public void removeFromAuthor(User u) {
        this.author = null;
        u.getRecipes().remove(this);
    }
}
