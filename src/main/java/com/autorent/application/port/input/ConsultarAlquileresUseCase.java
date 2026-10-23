package com.autorent.application.port.input;

import com.autorent.domain.model.Alquiler;

public interface ConsultarAlquileresUseCase {
    Alquiler obtenerAlquilerPorId(Long id);
}
