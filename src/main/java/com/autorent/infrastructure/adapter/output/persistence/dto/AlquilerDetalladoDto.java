package com.autorent.infrastructure.adapter.output.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerDetalladoDto {

    private Long alquilerId;
    private int clienteId;
    private String clienteNombre;
    private String clienteDocumento;
    private int autoId;
    private String autoPlaca;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadDias;
    private double valorTotal;
    private String estadoAlquiler;
}