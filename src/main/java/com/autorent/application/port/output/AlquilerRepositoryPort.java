package com.autorent.application.port.output;

import com.autorent.domain.model.Alquiler;

import java.util.List;

public interface AlquilerRepositoryPort {
    Alquiler guardar(Alquiler alquiler);
    List<Alquiler> listarAlquileres();
}
