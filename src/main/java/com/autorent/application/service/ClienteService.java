package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.exception.ClienteDuplicadoException;
import com.autorent.domain.exception.DatoRequeridoException;
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
   if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty())
        throw new DatoRequeridoException("Nombre requerido");

   if (cliente.getDocumento() == null || cliente.getDocumento().trim().isEmpty())
        throw new DatoRequeridoException("Documento requerido");

   if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty())
       throw new DatoRequeridoException("Email requerido");

   String documentoLimpio = cliente.getDocumento().trim();
   cliente.setDocumento(documentoLimpio);

   if (clienteRepositoryPort.existsBydocumento(cliente.getDocumento()))
       throw new ClienteDuplicadoException("Documento existente");

   return clienteRepositoryPort.registrar(cliente);
}

@Override
    public List<Cliente> listarClientes(){
    return clienteRepositoryPort.listarClientes();
    }

}
