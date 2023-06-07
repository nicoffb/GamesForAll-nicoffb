package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("The user could not be found");
    }

    public UserNotFoundException(Long id) {
        super(String.format("The user with id %d could not be found", id));
    }
}
