package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.domain.model.Alquiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alquiler")
public class AlquilerController {
    private final CrearAlquilerUseCase crearAlquilerUseCase;

    public AlquilerController(CrearAlquilerUseCase crearAlquilerUseCase) {
        this.crearAlquilerUseCase = crearAlquilerUseCase;
    }

    public static class CrearAlquilerRequest {
        public Long clienteId;
        public Long autoId;
        public int cantidadDias;
    }

    @PostMapping
    public ResponseEntity<Alquiler> registraAlquiler(@RequestBody CrearAlquilerRequest request) {
        Alquiler alquilerCreado = crearAlquilerUseCase.crear(
                request.clienteId,
                request.autoId,
                request.cantidadDias);

        return new ResponseEntity<>(alquilerCreado, HttpStatus.CREATED);
    }
}