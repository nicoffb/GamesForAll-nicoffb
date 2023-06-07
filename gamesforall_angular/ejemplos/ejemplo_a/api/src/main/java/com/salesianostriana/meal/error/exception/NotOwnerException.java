package com.salesianostriana.meal.error.exception;

public class NotOwnerException extends SecurityException{
    public NotOwnerException() {
        super("Usted no tiene acceso a la gestión de otros establecimientos");
    }
}
