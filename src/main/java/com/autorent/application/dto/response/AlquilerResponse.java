package com.autorent.application.dto.response;

import com.autorent.domain.model.EstadoAlquiler;

import java.time.LocalDate;

public record AlquilerResponse(
        Long id,
        Long clienteId,
        Long autoId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        int cantidadDias,
        Double valorTotal,
        String estado
) {
}
