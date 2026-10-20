package com.autorent.application.dto.response;

public record AutoResponse(
        Long id,
        String placa,
        String marca,
        String modelo,
        int anio,
        double deposito,
        double precioPorDia,
        String estado

) {
}
