package com.jobhorizon.backend.seguridad.exception;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
}
