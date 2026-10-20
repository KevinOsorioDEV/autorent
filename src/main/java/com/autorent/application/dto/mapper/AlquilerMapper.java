package com.autorent.application.dto.mapper;

import com.autorent.application.dto.request.CrearAlquilerRequest;
import com.autorent.application.dto.response.AlquilerResponse;
import com.autorent.domain.model.Alquiler;
import org.springframework.stereotype.Component;

@Component
public class AlquilerMapper {
    public AlquilerResponse toResponse(Alquiler alquiler) {
        return new AlquilerResponse(
                alquiler.getAutoId(),
                alquiler.getClienteId(),
                alquiler.getAutoId(),
                alquiler.getFechaInicio(),
                alquiler.getFechaFin(),
                alquiler.getCantidadDias(),
                alquiler.getValorTotal(),
                alquiler.getEstado().toString()
        );

    }
    public Alquiler toDomain(CrearAlquilerRequest request){
        return Alquiler.builder()
                .clienteId(request.clienteId())
                .autoId(request.autoId())
                .cantidadDias(request.cantidadDias())
                .build();
    }
}
