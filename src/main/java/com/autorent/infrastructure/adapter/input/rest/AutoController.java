package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.port.input.ConsultarAutosUseCase;
import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.application.port.output.AutoRepositoryPort;
import com.autorent.domain.model.Auto;
import com.autorent.infrastructure.adapter.output.persistence.repository.AutoJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Auto> registrarAuto(@RequestBody Auto auto) {
        Auto autoCreado = registrarAutoUseCase.registrar(auto);
        return new ResponseEntity<>(autoCreado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Auto>> listarAutos() {
        List<Auto> autos = consultarAutosUseCase.listarAutos();
        return new ResponseEntity<>(autos, HttpStatus.OK);
    }

}
