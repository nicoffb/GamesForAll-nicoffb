package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException() {
        super("The category could not be found");
    }
}
