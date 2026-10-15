package com.autorent.application.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;

public record AlquilerDetalladoDto(
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
