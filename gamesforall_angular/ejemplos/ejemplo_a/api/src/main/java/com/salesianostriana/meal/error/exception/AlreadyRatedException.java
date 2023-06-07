package com.salesianostriana.meal.error.exception;

public class AlreadyRatedException extends BadRequestException{

    public AlreadyRatedException(){
        super("No puede valorar dos veces el mismo producto");
    }

}
