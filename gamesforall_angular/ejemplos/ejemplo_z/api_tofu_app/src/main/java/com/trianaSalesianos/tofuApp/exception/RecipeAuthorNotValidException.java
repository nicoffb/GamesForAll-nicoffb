package com.trianaSalesianos.tofuApp.exception;

public class RecipeAuthorNotValidException extends RuntimeException{
    public RecipeAuthorNotValidException() {
        super("The current user is not the author of the recipe");
    }
}
