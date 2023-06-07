package com.salesianostriana.meal.security.error;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String msg) {
        super(msg);
    }

}
