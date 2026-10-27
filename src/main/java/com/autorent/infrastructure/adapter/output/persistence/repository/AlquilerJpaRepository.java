package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilerJpaRepository extends JpaRepository<AlquilerEntity, Long> {
    boolean existsByCliente_IdAndEstado_Descripcion(Long clienteId, String descripcion);
}
