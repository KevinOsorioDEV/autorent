package com.autorent.application.port.input;

import com.autorent.domain.model.Auto;

public interface EditarAutoUseCase {
    Auto editar(Long id, Auto auto);
}
