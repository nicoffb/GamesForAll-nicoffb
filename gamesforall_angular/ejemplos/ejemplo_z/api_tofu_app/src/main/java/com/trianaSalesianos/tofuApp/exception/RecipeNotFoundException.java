package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class RecipeNotFoundException extends EntityNotFoundException {
    public RecipeNotFoundException() {
        super("The recipe could not be found");
    }
}
