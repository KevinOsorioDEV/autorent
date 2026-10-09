package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.model.Cliente;
import com.autorent.infrastructure.adapter.output.persistence.entity.ClienteEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientePersistenceAdapter implements ClienteRepositoryPort {
    private final ClienteJpaRepository clienteJpaRepository;

    public ClientePersistenceAdapter(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Cliente registrar(Cliente cliente){
        ClienteEntity entity = new ClienteEntity();
        entity.setNombre(cliente.getNombre());
        entity.setDocumento(cliente.getDocumento());
        entity.setEmail(cliente.getEmail());
        entity.setTelefono(cliente.getTelefono());

        ClienteEntity savedEntity = clienteJpaRepository.save(entity);
        cliente.setId(savedEntity.getId());

        return cliente;

    }
    public boolean existsBydocumento(String identificacion){
        return clienteJpaRepository.existsBydocumento(identificacion);

    }

}
