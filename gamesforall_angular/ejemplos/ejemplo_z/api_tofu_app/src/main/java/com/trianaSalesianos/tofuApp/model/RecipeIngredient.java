package com.trianaSalesianos.tofuApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class RecipeIngredient {
    @Builder.Default
    @EmbeddedId
    private RecipeIngredientPK id = new RecipeIngredientPK();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("id_recipe")
    @JoinColumn(name="id_recipe")
    private Recipe recipe;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("id_ingredient")
    @JoinColumn(name="id_ingredient")
    private Ingredient ingredient;
    double amount;
    String unit; //in cm, pieces,

}
