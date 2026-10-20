package com.autorent.domain.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {

        super("Email ya está registrado : " + email);
    }
}
