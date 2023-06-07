package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class IngredientNotFoundException extends EntityNotFoundException {
    public IngredientNotFoundException() {
        super("The ingredient could not be found");
    }
}
