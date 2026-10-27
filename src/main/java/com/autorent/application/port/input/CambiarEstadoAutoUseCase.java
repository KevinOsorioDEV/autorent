package com.autorent.application.port.input;

import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;

public interface CambiarEstadoAutoUseCase {
    Auto cambiarEstado(Long autoId, EstadoAuto nuevoEstado);
}
