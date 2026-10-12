package com.autorent.application.port.output;


import com.autorent.domain.model.Cliente;

import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente registrar(Cliente cliente);
    boolean existsBydocumento(String identificacion);
    Optional<Cliente> buscarPorId(Long id);
}
