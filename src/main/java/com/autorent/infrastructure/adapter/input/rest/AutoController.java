package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.domain.exception.AutoNoEncontradoException;
import com.autorent.domain.exception.PlacaDuplicadaException;
import com.autorent.domain.model.Auto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/autos")
public class AutoController {

    private final RegistrarAutoUseCase registrarAutoUseCase;
    private final ConsultarAutosUseCase consultarAutosUseCase;

    public AutoController(RegistrarAutoUseCase registrarAutoUseCase, ConsultarAutosUseCase consultarAutosUseCase) {
        this.registrarAutoUseCase = registrarAutoUseCase;
        this.consultarAutosUseCase = consultarAutosUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registrarAuto(@RequestBody Auto auto) {
        try {
        Auto autoCreado = registrarAutoUseCase.registrar(auto);
        return ResponseEntity.status(HttpStatus.CREATED).body(autoCreado);
        }catch (PlacaDuplicadaException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Mensaje", ex.getMessage()));
        }

    }

    @GetMapping
    public ResponseEntity<List<Auto>> listarAutos() {
        List<Auto> autos = consultarAutosUseCase.listarAutos();
        return new ResponseEntity<>(autos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAutosPorId(@PathVariable Long id) {
        try {
            Auto auto = consultarAutosUseCase.obtenerAutosPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(auto);
        } catch (AutoNoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje", ex.getMessage()));
        }
    }

    @GetMapping("/disponibles")
    public List<Auto> obtenerAutosDisponibles() {
        return consultarAutosUseCase.obtenerAutosDiponibles();
    }

}
