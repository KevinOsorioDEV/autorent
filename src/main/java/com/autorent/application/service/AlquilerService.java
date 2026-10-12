package com.autorent.application.service;

import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.exception.AutoNoDisponibleException;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.ClienteNoEncontradoException;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAlquiler;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AlquilerService implements CrearAlquilerUseCase {
    private final AlquilerRepositoryPort alquilerRepositoryPort;
    private final ClienteRepositoryPort clienteRepositoryPort;
    private final AutoRepositoryPort autoRepositoryPort;

    public AlquilerService(AlquilerRepositoryPort alquilerRepositoryPort,
                           ClienteRepositoryPort clienteRepositoryPort,
                           AutoRepositoryPort autoRepositoryPort) {
        this.alquilerRepositoryPort = alquilerRepositoryPort;
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.autoRepositoryPort = autoRepositoryPort;
    }

    @Override
    @Transactional
    public Alquiler crear(Long clienteId, Long autoId, int cantidadDias){
        if(cantidadDias <= 0){
            throw new RuntimeException("El cantidad de dias no puede ser menor a 0");
        }

        clienteRepositoryPort.buscarPorId(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));

        Auto auto = autoRepositoryPort.buscarPorId(autoId)
                .orElseThrow(() -> new AutoNoEncontradoException("auto no encontrado"));

        if (auto.getEstado() != EstadoAuto.DISPONIBLE){
            throw new AutoNoDisponibleException("Auto no disponible");
        }

        double valorTotal = auto.getPrecioPorDia()*cantidadDias;
        auto.setEstado(EstadoAuto.ALQUILADO);
        autoRepositoryPort.guardar(auto);

        Alquiler alquiler = Alquiler.builder()
                .clienteId(clienteId)
                .autoId(autoId)
                .fechaInicio(LocalDate.now())
                .cantidadDias(cantidadDias)
                .valorTotal(valorTotal)
                .estado(EstadoAlquiler.ACTIVO) //[cite: 5]
                .build();

        return alquilerRepositoryPort.guardar(alquiler);
    }

}
