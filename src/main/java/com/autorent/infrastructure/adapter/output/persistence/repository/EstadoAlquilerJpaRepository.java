package com.autorent.infrastructure.adapter.output.persistence.repository;

import com.autorent.infrastructure.adapter.output.persistence.entity.EstadoAlquilerEntity;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoAlquilerJpaRepository extends JpaRepository<EstadoAlquilerEntity, Integer> {

    Optional<EstadoAlquilerEntity> findByDescripcion(String descripcion);
}
