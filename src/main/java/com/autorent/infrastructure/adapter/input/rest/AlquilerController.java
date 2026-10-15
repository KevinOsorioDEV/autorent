package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.dto.AlquilerDetalladoDto;
import com.autorent.application.port.input.ConsultarAlquilerDetalldoUseCase;
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
    private final ConsultarAlquilerDetalldoUseCase consultarAlquilerDetalldoUseCase;

    public AlquilerController(CrearAlquilerUseCase crearAlquilerUseCase,
                              ConsultarAlquileresUseCase consultarAlquileresUseCase,ConsultarAlquilerDetalldoUseCase consultarAlquilerDetalldoUseCase) {
        this.crearAlquilerUseCase = crearAlquilerUseCase;
        this.consultarAlquileresUseCase = consultarAlquileresUseCase;
        this.consultarAlquilerDetalldoUseCase = consultarAlquilerDetalldoUseCase;
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

    @GetMapping("/detallados")
    public ResponseEntity<List<AlquilerDetalladoDto>> listarAlquileresDetallados() {
        List<AlquilerDetalladoDto> alquilerDetallado = consultarAlquilerDetalldoUseCase.listarTodosAlquilerDetallados();
        return new ResponseEntity<>(alquilerDetallado, HttpStatus.OK);
    }
    @GetMapping("/{id}/detallado")
    public AlquilerDetalladoDto obtenerDetallado(@PathVariable Long id) {
        return consultarAlquilerDetalldoUseCase.consultarAlquilerDetalladoPorId(id);
    }

    @PutMapping("/{id}/finalizar")
    public Alquiler finalizar(@PathVariable Long id) {
        return consultarAlquileresUseCase.finalizar(id);
    }
}