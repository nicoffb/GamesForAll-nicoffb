package com.salesianostriana.meal.error.exception;

public class RestaurantInUseException extends BadRequestException{
    public RestaurantInUseException(){
        super("No se puede borrar un restaurante al que se le hayan añadido platos. Por favor, eliminelos primero.");
    }
}
