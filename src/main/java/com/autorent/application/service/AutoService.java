package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    public class AutoService implements RegistrarAutoUseCase, ConsultarAutosUseCase {

    private final AutoRepositoryPort autoRepositoryPort;

    public AutoService(AutoRepositoryPort autoRepositoryPort) {
        this.autoRepositoryPort = autoRepositoryPort;
    }

    @Override
    public Auto registrar(Auto auto) {
        if (autoRepositoryPort.existsByPlaca(auto.getPlaca())) {
            throw new PlacaDuplicadaException(auto.getPlaca());
        }
        auto.setEstado(EstadoAuto.DISPONIBLE);

        return autoRepositoryPort.guardar(auto);
    }

    @Override
    public List<Auto> listarAutos() {
        return autoRepositoryPort.listarAutos();
    }

    @Override
    public Auto obtenerAutosPorId(Long id){
        return autoRepositoryPort.buscarPorId(id).orElseThrow(
                () -> new AutoNoEncontradoException("Auto no encontrado: " + id)

        );
    }

    @Override
    public List<Auto> obtenerAutosDiponibles() {
        return autoRepositoryPort.obtenerAutosDisponibles();
    }

}

