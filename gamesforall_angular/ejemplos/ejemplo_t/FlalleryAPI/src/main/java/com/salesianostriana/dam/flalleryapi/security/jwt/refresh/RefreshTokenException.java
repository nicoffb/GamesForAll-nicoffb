package com.salesianostriana.dam.flalleryapi.security.jwt.refresh;

import com.salesianostriana.dam.flalleryapi.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


