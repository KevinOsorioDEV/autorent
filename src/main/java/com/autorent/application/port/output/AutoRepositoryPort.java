package com.autorent.application.port.output;

import com.autorent.domain.model.Auto;

public interface AutoRepositoryPort {
    Auto guardar(Auto auto);
    boolean existsByPlaca(String placa);
}
