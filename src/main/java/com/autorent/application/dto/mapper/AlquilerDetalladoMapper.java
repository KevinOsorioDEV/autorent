package com.autorent.application.dto.mapper;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;
import com.autorent.domain.model.AlquilerDetallado;
import org.springframework.stereotype.Component;

@Component
public class AlquilerDetalladoMapper {
    public AlquilerDetalladoResponse toResponse(AlquilerDetallado detalladoDto) {
        return new AlquilerDetalladoResponse(
                detalladoDto.id(),
                detalladoDto.clienteId(),
                detalladoDto.clienteNombre(),
                detalladoDto.documento(),
                detalladoDto.autoId(),
                detalladoDto.placa(),
                detalladoDto.fechaInicio(),
                detalladoDto.fechaFin(),
                detalladoDto.cantidadDias(),
                detalladoDto.valorTotal(),
                detalladoDto.estado().name()
        );
    }
}
