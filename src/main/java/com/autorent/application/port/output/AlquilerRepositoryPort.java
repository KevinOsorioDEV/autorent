package com.autorent.application.port.output;

import com.autorent.domain.model.Alquiler;

public interface AlquilerRepositoryPort {
    Alquiler guardar(Alquiler alquiler);
}
