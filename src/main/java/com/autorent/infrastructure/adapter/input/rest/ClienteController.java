package com.autorent.infrastructure.adapter.input.rest;


import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.domain.exception.ClienteDuplicadoException;
import com.autorent.domain.exception.DatoRequeridoException;
import com.autorent.domain.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente){
       try {
           Cliente nuevoCliente = registrarClienteUseCase.registrarCliente(cliente);
           return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
       }catch (ClienteDuplicadoException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of( "Mensaje",e.getMessage()));
       }catch (DatoRequeridoException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensaje",e.getMessage()));
       }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes = consultarClientesUseCase.listarClientes();
        return new ResponseEntity<>(clientes,HttpStatus.OK);
    }
}
