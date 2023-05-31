package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyPlatformListException extends EntityNotFoundException {

    public EmptyPlatformListException() {
        super("No se han encontrado plataformas");
    }


}
