package com.salesianostriana.gamesforall.security.jwt.refresh;

import com.salesianostriana.gamesforall.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {

    public RefreshTokenException(String msg) {
        super(msg);
    }

}


