package com.autorent.application.dto.request;

public record CrearClienteRequest (
        String nombre,
        String documento,
        String email,
        String telefono
) {
}
