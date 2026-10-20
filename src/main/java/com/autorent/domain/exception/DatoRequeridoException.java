package com.autorent.domain.exception;

public class DatoRequeridoException extends RuntimeException {
    public DatoRequeridoException(String message) {
        super("Dato incorrecto: " + message);
    }
}
