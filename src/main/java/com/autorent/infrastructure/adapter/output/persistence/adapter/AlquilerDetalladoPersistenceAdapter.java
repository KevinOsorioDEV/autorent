package com.autorent.infrastructure.adapter.output.persistence.adapter;

import com.autorent.application.dto.AlquilerDetalladoDto;
import com.autorent.application.port.output.AlquilerDetalladoRepositoryPort;
import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerDetalladoEntity;
import com.autorent.infrastructure.adapter.output.persistence.repository.AlquilerDetalladoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlquilerDetalladoPersistenceAdapter implements AlquilerDetalladoRepositoryPort {

    private final AlquilerDetalladoJpaRepository alquilerDetalladoJpaRepository;

    public AlquilerDetalladoPersistenceAdapter(AlquilerDetalladoJpaRepository alquilerDetalladoJpaRepository) {
        this.alquilerDetalladoJpaRepository = alquilerDetalladoJpaRepository;
    }

    @Override
    public Optional<AlquilerDetalladoDto> consultarAlquilerDetalladoPorId(Long id) {
        return alquilerDetalladoJpaRepository.findById(id).map(this::toDomain);
    }
    @Override
    public List<AlquilerDetalladoDto> listarTodosAlquilerDetallados() {
        return alquilerDetalladoJpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private AlquilerDetalladoDto toDomain(AlquilerDetalladoEntity entity) {
        return new AlquilerDetalladoDto(
                entity.getId(),
                entity.getClienteId(),
                entity.getNombre(),
                entity.getDocumento(),
                entity.getAutoId(),
                entity.getPlaca(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getCantidadDias(),
                entity.getValorTotal(),
                entity.getEstado()
        );
    }
}
