package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.dto.mapper.AutoMapper;
import com.autorent.application.dto.request.CrearAutoRequest;
import com.autorent.application.dto.response.ApiErrorResponse;
import com.autorent.application.dto.response.AutoResponse;
import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.DatoInvalidoException;
import com.autorent.domain.exception.DatoRequeridoException;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/autos")
public class AutoController {

    private final RegistrarAutoUseCase registrarAutoUseCase;
    private final ConsultarAutosUseCase consultarAutosUseCase;
    private final AutoMapper autoMapper;

    public AutoController(RegistrarAutoUseCase registrarAutoUseCase,
                          ConsultarAutosUseCase consultarAutosUseCase,
                          AutoMapper autoMapper) {
        this.registrarAutoUseCase = registrarAutoUseCase;
        this.consultarAutosUseCase = consultarAutosUseCase;
        this.autoMapper = autoMapper;
    }

    @PostMapping
    public ResponseEntity<?> registrarAuto(@RequestBody CrearAutoRequest request) {
        try {
            Auto auto = autoMapper.toDomain(request);
            Auto autoCreado = registrarAutoUseCase.registrar(auto);
            AutoResponse response = autoMapper.toResponse(autoCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (PlacaDuplicadaException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body
                    (new ApiErrorResponse(409,
                            "Conflict", ex.getMessage()));
        }catch (DatoRequeridoException | DatoInvalidoException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse(400,
                            "Bad Request", ex.getMessage()));

        }

    }

    @GetMapping
    public ResponseEntity<?> listarAutos() {
        List<Auto> autos = consultarAutosUseCase.listarAutos();
        List<AutoResponse> responses = autos.stream()
                .map(autoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAutosPorId(@PathVariable Long id) {
        try {
            Auto auto = consultarAutosUseCase.obtenerAutosPorId(id);
            AutoResponse response = autoMapper.toResponse(auto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AutoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje", ex.getMessage()));
        }
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> obtenerAutosDisponibles() {
        List<Auto> autos = consultarAutosUseCase.obtenerAutosDiponibles();
        List<AutoResponse> autosResponse = autos.stream()
                .map(autoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(autosResponse);
    }

}
