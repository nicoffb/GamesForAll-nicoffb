package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super("El usuario no se ha encontrado");
    }

    public UserNotFoundException(UUID id) {
        super(String.format("El usuario no se ha encontrado", id));
    }

}
