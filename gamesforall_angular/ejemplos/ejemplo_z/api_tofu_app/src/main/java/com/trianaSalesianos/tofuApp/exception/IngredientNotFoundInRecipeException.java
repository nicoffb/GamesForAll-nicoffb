package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class IngredientNotFoundInRecipeException  extends EntityNotFoundException {

    public IngredientNotFoundInRecipeException() {
        super("The ingredient could not be found inside the recipe");
    }

}
