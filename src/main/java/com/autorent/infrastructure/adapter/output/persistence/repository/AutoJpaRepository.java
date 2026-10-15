package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import com.autorent.infrastructure.adapter.output.persistence.entity.AutoEntity;
import com.autorent.infrastructure.adapter.output.persistence.entity.EstadoAutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AutoJpaRepository extends JpaRepository<AutoEntity, Long> {
        boolean existsByPlaca(String placa);
        List<AutoEntity> findByEstado(EstadoAutoEntity estado);
}
