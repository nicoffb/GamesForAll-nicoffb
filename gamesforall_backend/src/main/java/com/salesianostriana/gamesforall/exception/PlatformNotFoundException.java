package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class PlatformNotFoundException extends EntityNotFoundException {

    public PlatformNotFoundException() {
        super("La plataforma no se ha encontrado");
    }


}
