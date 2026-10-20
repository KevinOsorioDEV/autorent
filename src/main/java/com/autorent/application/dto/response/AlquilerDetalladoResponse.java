package com.autorent.application.dto.response;

import java.time.LocalDate;

public record AlquilerDetalladoResponse(
        Long id,
        Integer clienteId,
        String clienteNombre,
        String documento,
        Integer autoId,
        String Placa,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer cantidadDias,
        Double valorTotal,
        String estado
) {
}
