package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.model.Cliente;
import com.autorent.domain.model.EstadoAlquiler;
import com.autorent.infrastructure.adapter.output.persistence.entity.ClienteEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

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

    public boolean existsByemail(String email){
        return clienteJpaRepository.existsByemail(email);
    }

    public boolean existsBydocumento(String identificacion){
        return clienteJpaRepository.existsBydocumento(identificacion);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteJpaRepository.findById(id).map(this::toDomain);

    }

    @Override
    public List<Cliente> listarClientes(){
        return clienteJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private Cliente toDomain(ClienteEntity entity){
        return Cliente.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .documento(entity.getDocumento())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .build();
    }


}
