package com.autorent.application.dto.mapper;

import com.autorent.application.dto.request.CrearAutoRequest;
import com.autorent.application.dto.response.AutoResponse;
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
                auto.getPrecioPorDia(),
                auto.getDeposito(),
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

}
