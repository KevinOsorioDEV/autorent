package com.autorent.domain.exception;

public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String message) {
        super("Dato inválido: " + message);
    }
}
