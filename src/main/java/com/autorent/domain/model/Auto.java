package com.autorent.domain.model;


import com.autorent.domain.exception.AutoNoDisponibleException;
import com.autorent.domain.exception.CambioEstadoNoPermitidoException;
import com.autorent.domain.exception.EdicionNoPermitidaException;
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


    public void cambiarEstadoManual(EstadoAuto nuevoEstado) {
        if (estado == EstadoAuto.ALQUILADO) {
            throw new CambioEstadoNoPermitidoException(
                    "El auto está alquilado. Solo vuelve a disponible al finalizar el alquiler");
        }
        if (nuevoEstado == EstadoAuto.ALQUILADO) {
            throw new CambioEstadoNoPermitidoException(
                    "Un auto solo pasa a alquilado al crear un alquiler");
        }
        if (estado == nuevoEstado) {
            throw new CambioEstadoNoPermitidoException("El auto ya está en " + nuevoEstado);
        }
        estado = nuevoEstado;
    }

    public void actualizarDatos(Auto datos) {
        if (estado == EstadoAuto.ALQUILADO) {
            throw new EdicionNoPermitidaException(
                    "El auto está alquilado. Se puede editar cuando finalice el alquiler");
        }
        placa = datos.getPlaca();
        marca = datos.getMarca();
        modelo = datos.getModelo();
        anio = datos.getAnio();
        deposito = datos.getDeposito();
        precioPorDia = datos.getPrecioPorDia();
    }

}
