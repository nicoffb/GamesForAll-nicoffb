package com.salesianostriana.meal.error.exception;

public class InvalidSearchException extends BadRequestException{

    public InvalidSearchException(){
        super("Los parámetros de búsqueda no son válidos");
    }

}
