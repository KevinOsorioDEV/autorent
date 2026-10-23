package com.autorent.application.port.input;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;
import com.autorent.domain.model.AlquilerDetallado;

import java.util.List;

public interface ConsultarAlquilerDetalladoUseCase {
    AlquilerDetallado consultarAlquilerDetalladoPorId(Long id);
    List<AlquilerDetallado> listarTodosAlquilerDetallados();
}
