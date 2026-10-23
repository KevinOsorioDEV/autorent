package com.autorent.domain.model;

import com.autorent.domain.exception.AlquilerFinalizadoException;
import com.autorent.domain.exception.CantidadDiasInvalidaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alquiler {
    private Long id;
    private Long clienteId;
    private Long autoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cantidadDias;
    private Double valorTotal;
    private EstadoAlquiler estado;

    public static Alquiler iniciar(Long clienteId, Auto auto, int cantidadDias) {
        if (cantidadDias <= 0) {
            throw new CantidadDiasInvalidaException(cantidadDias);
        }
        auto.alquilar();
        LocalDate hoy = LocalDate.now();
        return Alquiler.builder()
                .clienteId(clienteId)
                .autoId(auto.getId())
                .fechaInicio(hoy)
                .fechaFin(hoy.plusDays(cantidadDias))
                .cantidadDias(cantidadDias)
                .valorTotal(auto.getPrecioPorDia() * cantidadDias)
                .estado(EstadoAlquiler.ACTIVO)
                .build();
    }

    public void finalizar() {
        if (estado == EstadoAlquiler.FINALIZADO) {
            throw new AlquilerFinalizadoException("Alquiler se encuentra finalizado: " + id);
        }
        estado = EstadoAlquiler.FINALIZADO;
    }
}
