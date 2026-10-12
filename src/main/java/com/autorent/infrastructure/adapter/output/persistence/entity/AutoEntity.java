package com.autorent.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "auto")
public class AutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String placa;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false)
    private Integer anio;

    @Column(name = "deposito")
    private Double deposito;

    @Column(name = "precio_dia", nullable = false)
    private Double precioPorDia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoAutoEntity estado;

}
