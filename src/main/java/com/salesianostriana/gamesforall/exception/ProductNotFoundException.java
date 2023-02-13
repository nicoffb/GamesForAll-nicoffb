package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException() {
        super("El producto no se ha encontrado");
    }

    public ProductNotFoundException(Long id) {
        super(String.format("El producto con el id %d no se ha encontrado", id));
    }

}
