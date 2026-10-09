package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.port.input.RegistrarAutoUseCase;
import com.autorent.domain.model.Auto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/autos")
public class AutoController {

private final RegistrarAutoUseCase registrarAutoUseCase;

public AutoController(RegistrarAutoUseCase registrarAutoUseCase) {
    this.registrarAutoUseCase = registrarAutoUseCase;
}

@PostMapping
    public ResponseEntity<Auto> registrarAuto(@RequestBody Auto auto) {
    Auto autoCreado = registrarAutoUseCase.registrar(auto);
    return new ResponseEntity<>(autoCreado, HttpStatus.CREATED);
}
}
