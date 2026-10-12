package com.autorent.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Data
@Entity
@Table(name = "estado_alquiler")
public class EstadoAlquilerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String descripcion;
}
