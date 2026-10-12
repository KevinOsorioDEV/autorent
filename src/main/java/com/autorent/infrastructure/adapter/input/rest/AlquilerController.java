package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.port.input.ConsultarAlquileresUseCase;
import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.domain.model.Alquiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alquiler")
public class AlquilerController {
    private final CrearAlquilerUseCase crearAlquilerUseCase;
    private final ConsultarAlquileresUseCase consultarAlquileresUseCase;

    public AlquilerController(CrearAlquilerUseCase crearAlquilerUseCase,
                              ConsultarAlquileresUseCase consultarAlquileresUseCase) {
        this.crearAlquilerUseCase = crearAlquilerUseCase;
        this.consultarAlquileresUseCase = consultarAlquileresUseCase;
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

    @GetMapping
    public ResponseEntity<List<Alquiler>> listarAlquileres() {
        List<Alquiler> alquileres = consultarAlquileresUseCase.listarAlquileres();
        return new ResponseEntity<>(alquileres, HttpStatus.OK);
    }
}