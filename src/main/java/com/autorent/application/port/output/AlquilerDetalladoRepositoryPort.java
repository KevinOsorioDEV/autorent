package com.autorent.application.port.output;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;

import java.util.List;
import java.util.Optional;

public interface AlquilerDetalladoRepositoryPort {
Optional<AlquilerDetalladoResponse> consultarAlquilerDetalladoPorId(Long id);
List<AlquilerDetalladoResponse> listarTodosAlquilerDetallados();
}
