package com.autorent.domain.model;

import java.time.LocalDate;

public record AlquilerDetallado(
        Long id, Integer clienteId, String clienteNombre, String documento,
        Integer autoId, String placa, LocalDate fechaInicio, LocalDate fechaFin,
        Integer cantidadDias, Double valorTotal, EstadoAlquiler estado
) {
}
