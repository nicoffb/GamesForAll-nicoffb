package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class DietDaysNotFoundException extends EntityNotFoundException {
    public DietDaysNotFoundException() {
        super("The diet day could not be found");
    }
}
