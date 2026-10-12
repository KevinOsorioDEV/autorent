package com.autorent.application.port.input;

import com.autorent.domain.model.Cliente;

import java.util.List;

public interface ConsultarClientesUseCase {
    List<Cliente> listarClientes();
}
