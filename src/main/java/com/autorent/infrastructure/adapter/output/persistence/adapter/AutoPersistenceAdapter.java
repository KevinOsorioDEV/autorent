package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import com.autorent.infrastructure.adapter.output.persistence.entity.AutoEntity;
import com.autorent.infrastructure.adapter.output.persistence.entity.EstadoAutoEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.AutoJpaRepository;
import com.autorent.infrastructure.adapter.output.persistence.repository.EstadoAutoJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class AutoPersistenceAdapter implements AutoRepositoryPort {

    private final AutoJpaRepository autoRepository;
    private final EstadoAutoJpaRepository estadoRepository;

    public AutoPersistenceAdapter(AutoJpaRepository autoRepository, EstadoAutoJpaRepository estadoRepository) {
        this.autoRepository = autoRepository;
        this.estadoRepository = estadoRepository;
    }

    @Override
    public Auto guardar(Auto auto) {
        AutoEntity entity = new AutoEntity();
        entity.setPlaca(auto.getPlaca());
        entity.setMarca(auto.getMarca());
        entity.setModelo(auto.getModelo());
        entity.setAnio(auto.getAnio());
        entity.setDeposito(auto.getDeposito());
        entity.setPrecioPorDia(auto.getPrecioPorDia());

        // Buscamos el estado en la base de datos (PostgreSQL) usando el valor del ENUM
        EstadoAutoEntity estadoEntity = estadoRepository.findByDescripcion(auto.getEstado().name())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado en BD"));

        entity.setEstado(estadoEntity);

        AutoEntity savedEntity = autoRepository.save(entity);
        auto.setId(savedEntity.getId()); // Actualizamos el ID del dominio
        // con el generado por la BD

        return auto;
    }

    public boolean existsByPlaca(String placa) {
        return autoRepository.existsByPlaca(placa);
    }
}