package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.Cliente;
import com.autorent.domain.model.EstadoAlquiler;
import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerEntity;
import com.autorent.infrastructure.adapter.output.persistence.entity.ClienteEntity;
import com.autorent.infrastructure.adapter.output.persistence.entity.EstadoAlquilerEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.AlquilerJpaRepository;
import com.autorent.infrastructure.adapter.output.persistence.repository.AutoJpaRepository;
import com.autorent.infrastructure.adapter.output.persistence.repository.ClienteJpaRepository;
import com.autorent.infrastructure.adapter.output.persistence.repository.EstadoAlquilerJpaRepository;
import jakarta.persistence.Column;
import jakarta.websocket.ClientEndpoint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlquilerPersistenceAdapter implements AlquilerRepositoryPort {
    private final AlquilerJpaRepository alquilerJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final AutoJpaRepository autoJpaRepository;
    private final EstadoAlquilerJpaRepository estadoAlquilerJpaRepository;

    public AlquilerPersistenceAdapter(AlquilerJpaRepository alquilerJpaRepository,
                                      ClienteJpaRepository clienteJpaRepository,
                                      AutoJpaRepository autoJpaRepository,
                                      EstadoAlquilerJpaRepository estadoAlquilerJpaRepository) {
        this.alquilerJpaRepository = alquilerJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
        this.autoJpaRepository = autoJpaRepository;
        this.estadoAlquilerJpaRepository = estadoAlquilerJpaRepository;
    }

    @Override
    public Alquiler guardar(Alquiler alquiler) {
        AlquilerEntity entity = new AlquilerEntity();

        if (alquiler.getId() != null) {
            entity.setId(alquiler.getId());
        }
        entity.setCliente(clienteJpaRepository.findById(alquiler.getClienteId()).orElseThrow());
        entity.setAuto(autoJpaRepository.findById(alquiler.getAutoId()).orElseThrow());

        EstadoAlquilerEntity estadoEntity = estadoAlquilerJpaRepository.findByDescripcion(alquiler.getEstado().name())
                .orElseThrow(() -> new RuntimeException("Estado alquiler no encontrado"));
        entity.setEstado(estadoEntity);

        entity.setFechaInicio(alquiler.getFechaInicio());
        entity.setFechaFin(alquiler.getFechaFin());
        entity.setCantidadDias(alquiler.getCantidadDias());
        entity.setValorTotal(alquiler.getValorTotal());

        AlquilerEntity savedEntity = alquilerJpaRepository.save(entity);
        alquiler.setId(savedEntity.getId());

        return alquiler;
    }

    @Override
    public Optional<Alquiler> buscarPorId(Long id){
        return alquilerJpaRepository.findById(id).map(this::toDomain);
    }

    private Alquiler toDomain(AlquilerEntity entity) {
        return Alquiler.builder()
                .id(entity.getId())
                .clienteId(entity.getCliente().getId())
                .autoId(entity.getAuto().getId())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .cantidadDias(entity.getCantidadDias())
                .valorTotal(entity.getValorTotal())
                .estado(EstadoAlquiler.valueOf(entity.getEstado().getDescripcion()))
                .build();
    }


}
