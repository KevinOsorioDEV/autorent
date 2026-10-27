package com.autorent.application.dto.mapper;

import com.autorent.application.dto.request.CrearAutoRequest;
import com.autorent.application.dto.response.AutoResponse;
import com.autorent.domain.exception.DatoInvalidoException;
import com.autorent.domain.exception.DatoRequeridoException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Component;

@Component
public class AutoMapper {
    public AutoResponse toResponse(Auto auto) {
        return new AutoResponse(
                auto.getId(),
                auto.getPlaca(),
                auto.getMarca(),
                auto.getModelo(),
                auto.getAnio(),
                auto.getDeposito(),
                auto.getPrecioPorDia(),
                auto.getEstado().toString()
        );
    }

    public Auto toDomain(CrearAutoRequest request) {
        return Auto.builder()
                .placa(request.placa())
                .marca(request.marca())
                .modelo(request.modelo())
                .anio(request.anio())
                .deposito(request.deposito())
                .precioPorDia(request.precioPorDia())
                .estado(EstadoAuto.DISPONIBLE)
                .build();
    }
    public EstadoAuto toEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new DatoRequeridoException("El estado es obligatorio");
        }
        try {
            return EstadoAuto.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DatoInvalidoException("Estado no válido: " + estado + ". Usa DISPONIBLE o MANTENIMIENTO");
        }
    }
}
