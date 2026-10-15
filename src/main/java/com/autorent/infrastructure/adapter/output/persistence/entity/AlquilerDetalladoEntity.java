package com.autorent.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "vw_alquileres_detalle")
@Immutable
public class AlquilerDetalladoEntity {
    @Id
    private Long id;
    private Integer clienteId;
    private String nombre;
    private String documento;
    private Integer autoId;
    private String placa;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cantidadDias;
    private Double valorTotal;
    private String estado;
}
