package com.autorent.application.service;

import com.autorent.application.port.input.CambiarEstadoAutoUseCase;
import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.EditarAutoUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.DatoInvalidoException;
import com.autorent.domain.exception.DatoRequeridoException;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
    public class AutoService implements RegistrarAutoUseCase, ConsultarAutosUseCase, CambiarEstadoAutoUseCase,
        EditarAutoUseCase {

    private final AutoRepositoryPort autoRepositoryPort;

    public AutoService(AutoRepositoryPort autoRepositoryPort) {
        this.autoRepositoryPort = autoRepositoryPort;
    }


            @Override
    public Auto registrar(Auto auto) {
        validarDatos(auto);

        String placa = auto.getPlaca().trim();

        if (autoRepositoryPort.existsByPlaca(placa)) {
            throw new PlacaDuplicadaException(placa);
        }

        auto.setPlaca(placa);
        auto.setMarca(auto.getMarca().trim());
        auto.setModelo(auto.getModelo().trim());
        auto.setDeposito(auto.getDeposito());
        auto.setPrecioPorDia(auto.getPrecioPorDia());
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


    @Override
    public Auto cambiarEstado(Long autoId, EstadoAuto nuevoEstado) {
        Auto auto = autoRepositoryPort.buscarPorId(autoId).orElseThrow(
                () -> new AutoNoEncontradoException("Auto no encontrado: " + autoId)
        );
        auto.cambiarEstadoManual(nuevoEstado);
        return autoRepositoryPort.guardar(auto);
    }

    @Override
    public Auto editar(Long id, Auto datos) {
        Auto auto = autoRepositoryPort.buscarPorId(id).orElseThrow(
                () -> new AutoNoEncontradoException("Auto no encontrado: " + id)
        );
        validarDatos(datos);

        String placa = datos.getPlaca().trim();
        if (!placa.equalsIgnoreCase(auto.getPlaca()) && autoRepositoryPort.existsByPlaca(placa)) {
            throw new PlacaDuplicadaException(placa);
        }
        datos.setPlaca(placa);
        datos.setMarca(datos.getMarca().trim());
        datos.setModelo(datos.getModelo().trim());

        auto.actualizarDatos(datos);
        return autoRepositoryPort.guardar(auto);
    }

    private void validarDatos(Auto auto) {
        if (auto == null) {
            throw new DatoRequeridoException(
                    "Los datos del auto son obligatorios"
            );
        }

        if (auto.getPlaca() == null || auto.getPlaca().isBlank()) {
            throw new DatoRequeridoException("La placa es obligatoria");
        }

        if (auto.getMarca() == null || auto.getMarca().isBlank()) {
            throw new DatoRequeridoException("La marca es obligatoria");
        }

        if (auto.getModelo() == null || auto.getModelo().isBlank()) {
            throw new DatoRequeridoException("El modelo es obligatorio");
        }

        if (auto.getDeposito() == null || auto.getDeposito()<=0) {
            throw new DatoRequeridoException("El deposito debee ser mayo a cero");
        }

        if (auto.getPrecioPorDia() == null || auto.getPrecioPorDia() <= 0) {
            throw new DatoInvalidoException(
                    "El precio por día debe ser mayor que cero"
            );
        }
    }

}

