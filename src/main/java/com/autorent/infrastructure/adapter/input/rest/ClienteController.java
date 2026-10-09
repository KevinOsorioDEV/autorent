package com.autorent.infrastructure.adapter.input.rest;


import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.domain.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final RegistrarClienteUseCase registrarClienteUseCase;

    public ClienteController(RegistrarClienteUseCase registrarClienteUseCase){
        this.registrarClienteUseCase = registrarClienteUseCase;
    }

    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente){
        Cliente clienteRegistrado = registrarClienteUseCase.registrarCliente(cliente);
        return new ResponseEntity<>(clienteRegistrado,HttpStatus.CREATED);
    }
}
