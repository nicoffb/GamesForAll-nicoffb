package com.salesianostriana.gamesforall.exception;

import javax.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {

    public CategoryNotFoundException() {
        super("La categoría no se ha encontrado");
    }


}
