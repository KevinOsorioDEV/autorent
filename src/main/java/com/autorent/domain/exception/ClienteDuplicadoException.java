package com.autorent.domain.exception;

public class ClienteDuplicadoException extends RuntimeException {
    public ClienteDuplicadoException(String documento) {
        super("Esta identificacion ya se encuentra registrada: " + documento);
    }
}
