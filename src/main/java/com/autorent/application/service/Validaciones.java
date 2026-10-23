package com.autorent.application.service;

import com.autorent.domain.exception.DatoRequeridoException;

public class Validaciones {
    private Validaciones() {
    }

    static void requerido(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoRequeridoException(campo + " requerido");
        }
    }
}
