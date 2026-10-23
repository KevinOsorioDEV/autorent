package com.autorent.application.port.output;

import com.autorent.domain.model.AlquilerDetallado;

import java.util.List;
import java.util.Optional;

public interface AlquilerDetalladoRepositoryPort {
Optional<AlquilerDetallado> consultarAlquilerDetalladoPorId(Long id);
List<AlquilerDetallado> listarTodosAlquilerDetallados();
}
