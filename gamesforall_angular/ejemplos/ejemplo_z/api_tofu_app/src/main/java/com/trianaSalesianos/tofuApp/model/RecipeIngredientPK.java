package com.trianaSalesianos.tofuApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RecipeIngredientPK implements Serializable {
    private UUID id_recipe;
    private UUID id_ingredient;
}
