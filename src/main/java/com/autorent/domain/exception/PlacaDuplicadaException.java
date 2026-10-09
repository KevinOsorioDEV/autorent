package com.autorent.domain.exception;

public class PlacaDuplicadaException extends RuntimeException {
    public PlacaDuplicadaException(String placa) {
    super("Ya existe un auto registrado con la placa: " + placa);
    }
}
