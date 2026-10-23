package com.autorent.application.dto.request;

public record CrearAutoRequest(
        String placa,
        String marca,
        String modelo,
        int anio,
        double deposito,
        double precioPorDia
) { }
