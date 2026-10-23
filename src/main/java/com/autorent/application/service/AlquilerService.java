package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarAlquilerDetalladoUseCase;
import com.autorent.application.port.input.ConsultarAlquileresUseCase;
import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.application.port.input.FinalizarAlquilerUseCase;
import com.autorent.application.port.output.AlquilerDetalladoRepositoryPort;
import com.autorent.application.port.output.AlquilerRepositoryPort;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.application.port.output.ClienteRepositoryPort;
import com.autorent.domain.exception.*;
import com.autorent.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerService implements CrearAlquilerUseCase,
        ConsultarAlquileresUseCase, ConsultarAlquilerDetalladoUseCase, FinalizarAlquilerUseCase {
    private final AlquilerRepositoryPort alquilerRepositoryPort;
    private final ClienteRepositoryPort clienteRepositoryPort;
    private final AutoRepositoryPort autoRepositoryPort;
    private final AlquilerDetalladoRepositoryPort alquilerDetalladoRepositoryPort;

    public AlquilerService(AlquilerRepositoryPort alquilerRepositoryPort, ClienteRepositoryPort clienteRepositoryPort, AutoRepositoryPort autoRepositoryPort, AlquilerDetalladoRepositoryPort alquilerDetalladoRepositoryPort) {
        this.alquilerRepositoryPort = alquilerRepositoryPort;
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.autoRepositoryPort = autoRepositoryPort;
        this.alquilerDetalladoRepositoryPort = alquilerDetalladoRepositoryPort;
    }

    @Override
    @Transactional
    public Alquiler crear(Long clienteId, Long autoId, int cantidadDias) {
        if (clienteId == null) {
            throw new DatoRequeridoException("clienteId requerido");
        }
        if (autoId == null) {
            throw new DatoRequeridoException("autoId requerido");
        }
        if (cantidadDias <= 0) {
            throw new CantidadDiasInvalidaException(cantidadDias);
        }

        clienteRepositoryPort.buscarPorId(clienteId).orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado: " + clienteId));

        Auto auto = autoRepositoryPort.buscarPorId(autoId).orElseThrow(() -> new AutoNoEncontradoException("auto no encontrado: " + autoId));

        if (auto.getEstado() != EstadoAuto.DISPONIBLE) {
            throw new AutoNoDisponibleException("Auto no disponible");
        }

        double valorTotal = auto.getPrecioPorDia() * cantidadDias;
        auto.setEstado(EstadoAuto.ALQUILADO);
        autoRepositoryPort.guardar(auto);

        Alquiler alquiler = Alquiler.builder().clienteId(clienteId).autoId(autoId).fechaInicio(LocalDate.now()).fechaFin(LocalDate.now().plusDays(cantidadDias)).cantidadDias(cantidadDias).valorTotal(valorTotal).estado(EstadoAlquiler.ACTIVO).build();

        return alquilerRepositoryPort.guardar(alquiler);
    }

    @Override
    public List<AlquilerDetallado> listarTodosAlquilerDetallados() {
        return alquilerDetalladoRepositoryPort.listarTodosAlquilerDetallados();
    }


    @Override
    public AlquilerDetallado consultarAlquilerDetalladoPorId(Long id) {
        return alquilerDetalladoRepositoryPort.consultarAlquilerDetalladoPorId(id)
                .orElseThrow(() -> new AlquilerNoEncontradoException("Alquiler no encontrado: " + id.toString()));
    }

    @Override
    @Transactional
    public Alquiler finalizar(Long alquilerId) {
        Alquiler alquiler = alquilerRepositoryPort
                .buscarPorId(alquilerId).orElseThrow(
                        () -> new AlquilerNoEncontradoException
                                ("Alquiler no encontrado: "
                                        + alquilerId.toString()));
        Auto auto = autoRepositoryPort.buscarPorId(alquiler
                .getAutoId())
                .orElseThrow(
                        () -> new AutoNoEncontradoException
                                ("Auto no encontrado: "
                                        + alquiler.getAutoId()
                                        .toString()));

        if (alquiler.getEstado() == EstadoAlquiler.FINALIZADO) {
            throw new AlquilerFinalizadoException("Alquiler se encuentra finalizado: " + alquilerId.toString());
        }

        alquiler.finalizar();
        auto.liberar();
        autoRepositoryPort.guardar(auto);

        return alquilerRepositoryPort.guardar(alquiler);

    }

    @Override
    public Alquiler obtenerAlquilerPorId(Long id) {
        return alquilerRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new AlquilerNoEncontradoException("Alquiler no encontrado: " + id));
    }
}
