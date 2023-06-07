package com.trianaSalesianos.tofuApp.security.refresh;

import com.trianaSalesianos.tofuApp.security.errorhandling.JwtTokenException;

public class RefreshTokenException extends JwtTokenException {
    public RefreshTokenException(String msg) {
        super(msg);
    }
}
