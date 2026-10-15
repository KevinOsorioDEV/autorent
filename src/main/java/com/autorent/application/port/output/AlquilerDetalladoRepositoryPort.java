package com.autorent.application.port.output;

import com.autorent.application.dto.AlquilerDetalladoDto;

import java.util.List;
import java.util.Optional;

public interface AlquilerDetalladoRepositoryPort {
Optional<AlquilerDetalladoDto> consultarAlquilerDetalladoPorId(Long id);
List<AlquilerDetalladoDto> listarTodosAlquilerDetallados();
}
