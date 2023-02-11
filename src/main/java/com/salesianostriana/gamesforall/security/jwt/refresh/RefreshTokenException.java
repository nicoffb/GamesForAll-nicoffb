package com.salesianostriana.gamesforall.security.jwt.refresh;

import net.openwebinars.springboot.restjwt.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


