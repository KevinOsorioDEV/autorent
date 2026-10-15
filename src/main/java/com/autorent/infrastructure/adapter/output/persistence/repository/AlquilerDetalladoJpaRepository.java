package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.infrastructure.adapter.output.persistence.entity.AlquilerDetalladoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilerDetalladoJpaRepository extends JpaRepository<AlquilerDetalladoEntity, Long> {
}
