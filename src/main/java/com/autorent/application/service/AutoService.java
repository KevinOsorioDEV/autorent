package com.autorent.application.service;

import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.DatoInvalidoException;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
    public class AutoService implements RegistrarAutoUseCase, ConsultarAutosUseCase {

    private final AutoRepositoryPort autoRepositoryPort;

    public AutoService(AutoRepositoryPort autoRepositoryPort) {
        this.autoRepositoryPort = autoRepositoryPort;
    }

    @Override
    public Auto registrar(Auto auto) {
        validarDatos(auto);
        String placaLimpia = auto.getPlaca().trim();
        if (autoRepositoryPort.existsByPlaca(placaLimpia)) {
            throw new PlacaDuplicadaException(placaLimpia);
        }
        auto.setPlaca(placaLimpia);
        auto.setMarca(auto.getMarca().trim());
        auto.setModelo(auto.getModelo().trim());
        auto.setEstado(EstadoAuto.DISPONIBLE);
        return autoRepositoryPort.guardar(auto);
    }

    private void validarDatos(Auto auto) {
        Validaciones.requerido(auto.getPlaca(), "Placa");
        Validaciones.requerido(auto.getMarca(), "Marca");
        Validaciones.requerido(auto.getModelo(), "Modelo");
        if (auto.getPrecioPorDia() == null || auto.getPrecioPorDia() <= 0) {
            throw new DatoInvalidoException("El precio por día debe ser mayor que cero");
        }
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

