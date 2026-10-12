package com.autorent.infrastructure.adapter.input.rest;


import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.domain.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final ConsultarClientesUseCase consultarClientesUseCase;

    public ClienteController(RegistrarClienteUseCase registrarClienteUseCase,
                             ConsultarClientesUseCase consultarClientesUseCase) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.consultarClientesUseCase = consultarClientesUseCase;
    }

    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente){
        Cliente clienteRegistrado = registrarClienteUseCase.registrarCliente(cliente);
        return new ResponseEntity<>(clienteRegistrado,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes = consultarClientesUseCase.listarClientes();
        return new ResponseEntity<>(clientes,HttpStatus.OK);
    }
}
