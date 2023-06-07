package com.salesianostriana.meal.security.jwt.refresh;


import com.salesianostriana.meal.security.error.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


