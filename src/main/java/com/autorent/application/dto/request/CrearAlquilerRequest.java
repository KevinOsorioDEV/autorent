package com.autorent.application.dto.request;

public record CrearAlquilerRequest
        (
                Long clienteId,
                Long autoId,
                int cantidadDias
        ) {
}
