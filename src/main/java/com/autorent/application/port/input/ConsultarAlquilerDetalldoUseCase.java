package com.autorent.application.port.input;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;

import java.util.List;

public interface ConsultarAlquilerDetalldoUseCase {
    AlquilerDetalladoResponse consultarAlquilerDetalladoPorId(Long id);
    List<AlquilerDetalladoResponse> listarTodosAlquilerDetallados();
}
