package com.autorent.domain.model;

import java.time.LocalDate;

public class Alquiler {
    private long id;
    private Long clienteId;
    private Long autoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cantidadDias;
    private Double valorTotal;
    private EstadoAlquiler estado;
}
