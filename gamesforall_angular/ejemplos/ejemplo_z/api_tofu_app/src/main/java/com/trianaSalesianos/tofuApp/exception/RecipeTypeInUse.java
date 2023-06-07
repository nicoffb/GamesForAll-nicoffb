package com.trianaSalesianos.tofuApp.exception;

public class RecipeTypeInUse extends RuntimeException{
    public RecipeTypeInUse() {
        super("The recipe type is already in use");
    }

}
