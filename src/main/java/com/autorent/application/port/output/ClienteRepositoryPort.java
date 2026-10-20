package com.autorent.application.port.output;


import com.autorent.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente registrar(Cliente cliente);
    boolean existsBydocumento(String identificacion);
    boolean existsByemail(String email);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listarClientes();
}
