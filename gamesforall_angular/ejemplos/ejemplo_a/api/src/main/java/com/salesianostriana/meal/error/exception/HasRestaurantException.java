package com.salesianostriana.meal.error.exception;

public class HasRestaurantException extends BadRequestException{
    public HasRestaurantException(){
        super("Por favor, elimine los restaurantes que gestione primero.");
    }
}
