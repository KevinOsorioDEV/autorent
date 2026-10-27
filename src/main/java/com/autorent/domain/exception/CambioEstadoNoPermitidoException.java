package com.autorent.domain.exception;

public class CambioEstadoNoPermitidoException extends RuntimeException {
    public CambioEstadoNoPermitidoException(String message) {
        super(message);
    }
}
