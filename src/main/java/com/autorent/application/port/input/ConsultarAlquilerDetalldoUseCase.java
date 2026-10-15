package com.autorent.application.port.input;

import com.autorent.application.dto.AlquilerDetalladoDto;
import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerDetalladoEntity;

import java.util.List;

public interface ConsultarAlquilerDetalldoUseCase {
    AlquilerDetalladoDto consultarAlquilerDetalladoPorId(Long id);
    List<AlquilerDetalladoDto> listarTodosAlquilerDetallados();
}
