package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class StepNotFoundException extends EntityNotFoundException {

    public StepNotFoundException() {
        super("The step could not be found");
    }

}
