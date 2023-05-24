package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyProductListException extends EntityNotFoundException {

    public EmptyProductListException() {
        super("No se han encontrado productos");
    }


}
