package com.autorent.application.port.input;

import com.autorent.domain.model.Cliente;

public interface RegistrarClienteUseCase {
    Cliente registrarCliente(Cliente cliente);
}
