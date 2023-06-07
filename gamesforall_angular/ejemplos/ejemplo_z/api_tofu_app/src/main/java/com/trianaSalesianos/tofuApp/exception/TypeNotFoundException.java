package com.trianaSalesianos.tofuApp.exception;

import javax.persistence.EntityNotFoundException;

public class TypeNotFoundException extends EntityNotFoundException {

    public TypeNotFoundException() {
        super("The type could not be found");}
}
