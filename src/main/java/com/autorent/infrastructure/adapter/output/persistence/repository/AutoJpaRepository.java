package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.infrastructure.adapter.output.persistence.entity.AutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoJpaRepository extends JpaRepository<AutoEntity, Long> {
    boolean existsByPlaca(String placa);
}
