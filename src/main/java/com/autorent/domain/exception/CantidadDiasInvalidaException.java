package com.autorent.domain.exception;

public class CantidadDiasInvalidaException extends RuntimeException {

    public CantidadDiasInvalidaException(int cantidadDias) {
        super(
                "La cantidad de días debe ser mayor que cero, valor recibido: "
                        + cantidadDias
        );
    }
}