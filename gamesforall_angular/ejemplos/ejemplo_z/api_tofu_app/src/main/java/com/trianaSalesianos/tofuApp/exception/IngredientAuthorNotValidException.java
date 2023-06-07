package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class IngredientAuthorNotValidException extends EntityNotFoundException {

    public IngredientAuthorNotValidException() {
        super("The author of the ingredient coult not be found");
    }

}
