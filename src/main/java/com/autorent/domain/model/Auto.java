package com.autorent.domain.model;


import com.autorent.domain.exception.AutoNoDisponibleException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auto {

    private Long id;
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private Double deposito;
    private Double precioPorDia;
    private EstadoAuto estado;

    public void alquilar() {
        if (estado != EstadoAuto.DISPONIBLE) {
            throw new AutoNoDisponibleException("Auto no disponible");
        }
        estado = EstadoAuto.ALQUILADO;
    }

    public void liberar() {
        estado = EstadoAuto.DISPONIBLE;
    }

}
