package com.autorent.infrastructure.adapter.output.persistence.repository;


import com.autorent.infrastructure.adapter.output.persistence.entity.EstadoAutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoAutoJpaRepository extends JpaRepository<EstadoAutoEntity, Integer> {
    Optional<EstadoAutoEntity> findByDescripcion(String descripcion);
}
