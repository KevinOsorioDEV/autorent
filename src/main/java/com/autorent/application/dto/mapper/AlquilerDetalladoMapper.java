package com.autorent.application.dto.mapper;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;
import com.autorent.infrastructure.adapter.output.persistence.dto.AlquilerDetalladoDto;
import org.springframework.stereotype.Component;

@Component
public class AlquilerDetalladoMapper {
    public AlquilerDetalladoResponse toResponse(AlquilerDetalladoDto detalladoDto) {
        return new AlquilerDetalladoResponse(
                detalladoDto.getAlquilerId(),
                detalladoDto.getClienteId(),
                detalladoDto.getClienteNombre(),
                detalladoDto.getClienteDocumento(),
                detalladoDto.getAutoId(),
                detalladoDto.getAutoPlaca(),
                detalladoDto.getFechaInicio(),
                detalladoDto.getFechaFin(),
                detalladoDto.getCantidadDias(),
                detalladoDto.getValorTotal(),
                detalladoDto.getEstadoAlquiler()
        );
    }
}
