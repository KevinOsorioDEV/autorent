package com.autorent.application.port.input;

import com.autorent.domain.model.Alquiler;

import java.util.List;

public interface ConsultarAlquileresUseCase {
    Alquiler finalizar(Long id);
}
