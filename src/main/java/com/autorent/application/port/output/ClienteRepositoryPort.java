package com.autorent.application.port.output;


import com.autorent.domain.model.Cliente;

public interface ClienteRepositoryPort {
    Cliente registrar(Cliente cliente);
    boolean existsBydocumento(String identificacion);
}
