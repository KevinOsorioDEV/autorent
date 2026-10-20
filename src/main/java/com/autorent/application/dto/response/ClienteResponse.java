package com.autorent.application.dto.response;

public record ClienteResponse(
        Long id,
        String nombre,
        String documento,
        String email,
        String telefono
) {
}
