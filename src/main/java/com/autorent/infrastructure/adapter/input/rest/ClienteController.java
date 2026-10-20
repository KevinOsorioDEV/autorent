package com.autorent.infrastructure.adapter.input.rest;


import com.autorent.application.dto.mapper.ClienteMapper;
import com.autorent.application.dto.request.CrearClienteRequest;
import com.autorent.application.dto.response.ClienteResponse;
import com.autorent.application.port.input.ConsultarClientesUseCase;
import com.autorent.application.port.input.RegistrarClienteUseCase;
import com.autorent.domain.exception.ClienteDuplicadoException;
import com.autorent.domain.exception.DatoRequeridoException;
import com.autorent.domain.exception.EmailDuplicadoException;
import com.autorent.domain.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final ConsultarClientesUseCase consultarClientesUseCase;
    private final ClienteMapper clienteMapper;

    public ClienteController(RegistrarClienteUseCase registrarClienteUseCase,
                             ConsultarClientesUseCase consultarClientesUseCase,
                             ClienteMapper clienteMapper) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.consultarClientesUseCase = consultarClientesUseCase;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping
    public ResponseEntity<?> registrarCliente(@RequestBody CrearClienteRequest request){
       try {
           Cliente cliente = clienteMapper.toDomain(request);
           Cliente nuevoCliente = registrarClienteUseCase.registrarCliente(cliente);
           ClienteResponse response = clienteMapper.toResponse(nuevoCliente);
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
       }catch (ClienteDuplicadoException | EmailDuplicadoException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of( "Mensaje",e.getMessage()));
       }catch (DatoRequeridoException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Mensaje",e.getMessage()));
       }
    }

    @GetMapping
    public ResponseEntity<?> listarClientes(){
        List<Cliente> clientes =  consultarClientesUseCase.listarClientes();
        List<ClienteResponse> clienteResponses = clientes.stream()
                .map(clienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clienteResponses);
    }
}
