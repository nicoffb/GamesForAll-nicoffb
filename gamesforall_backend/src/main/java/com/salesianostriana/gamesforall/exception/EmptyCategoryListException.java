package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyCategoryListException extends EntityNotFoundException {

    public EmptyCategoryListException() {
        super("No se han encontrado categorias");
    }


}
