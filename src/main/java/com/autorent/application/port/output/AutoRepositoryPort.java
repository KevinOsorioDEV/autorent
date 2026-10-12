package com.autorent.application.port.output;

import com.autorent.domain.model.Auto;

import java.util.Optional;

public interface AutoRepositoryPort {
    Auto guardar(Auto auto);
    boolean existsByPlaca(String placa);
    Optional<Auto> buscarPorId(Long id);
}
