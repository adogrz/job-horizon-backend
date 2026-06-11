package com.jobhorizon.backend.seguridad.exception;

public class CuentaBloqueadaException extends RuntimeException {
    public CuentaBloqueadaException(String message) {
        super(message);
    }
}
