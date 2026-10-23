package com.autorent.application.port.input;

import com.autorent.domain.model.Alquiler;

public interface ConsultarAlquileresUseCase {
    Alquiler finalizar(Long id);
    Alquiler obtenerAlquilerPorId(Long id);
}
