package com.autorent.application.port.input;

import com.autorent.domain.model.Cliente;

public interface EditarClienteUseCase {
    Cliente editarCliente(Long id, Cliente cliente);
}
