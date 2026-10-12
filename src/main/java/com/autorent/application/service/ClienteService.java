package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.exception.ClienteDuplicadoException;
import com.autorent.domain.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements RegistrarClienteUseCase, ConsultarClientesUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

public ClienteService(ClienteRepositoryPort clienteRepositoryPort) {
    this.clienteRepositoryPort = clienteRepositoryPort;
}

@Override
    public Cliente registrarCliente(Cliente cliente){
    if(clienteRepositoryPort.existsBydocumento(cliente.getDocumento())){
        throw new ClienteDuplicadoException(cliente.getDocumento());
    }
    return clienteRepositoryPort.registrar(cliente);
}

@Override
    public List<Cliente> listarClientes(){
    return clienteRepositoryPort.listarClientes();
    }

}
