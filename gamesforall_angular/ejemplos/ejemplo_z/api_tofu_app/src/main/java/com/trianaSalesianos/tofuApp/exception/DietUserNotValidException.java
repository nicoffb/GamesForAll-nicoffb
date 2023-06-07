package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class DietUserNotValidException extends EntityNotFoundException {

    public DietUserNotValidException() {
        super("The user of the diet is not a valid user");
    }

}
