package com.autorent.application.service;

import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import com.autorent.domain.model.EstadoAuto;
import org.springframework.stereotype.Service;

@Service
public class AutoService implements RegistrarAutoUseCase {

    private final AutoRepositoryPort autoRepositoryPort;
    public AutoService(AutoRepositoryPort autoRepositoryPort) {
        this.autoRepositoryPort = autoRepositoryPort;
    }

    @Override
    public Auto registrar(Auto auto){
        if(autoRepositoryPort.existsByPlaca(auto.getPlaca())){
            throw new PlacaDuplicadaException(auto.getPlaca());
        }
        auto.setEstado(EstadoAuto.DISPONIBLE);

        return autoRepositoryPort.guardar(auto);
    }

}

