package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.dto.response.AlquilerDetalladoResponse;
import com.autorent.application.dto.mapper.AlquilerMapper;
import com.autorent.application.dto.request.CrearAlquilerRequest;
import com.autorent.application.dto.response.AlquilerResponse;
import com.autorent.application.dto.response.ApiErrorResponse;
import com.autorent.application.port.input.ConsultarAlquilerDetalladoUseCase;
import com.autorent.application.port.input.ConsultarAlquileresUseCase;
import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.application.port.input.FinalizarAlquilerUseCase;
import com.autorent.domain.exception.*;
import com.autorent.domain.model.Alquiler;
import com.autorent.domain.model.AlquilerDetallado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/alquiler")
public class AlquilerController {
    private final CrearAlquilerUseCase crearAlquilerUseCase;
    private final ConsultarAlquileresUseCase consultarAlquileresUseCase;
    private final ConsultarAlquilerDetalladoUseCase consultarAlquilerDetalldoUseCase;
    private final FinalizarAlquilerUseCase finalizarAlquilerUseCase;
    private final AlquilerMapper alquilerMapper;


    public AlquilerController(CrearAlquilerUseCase crearAlquilerUseCase,
                              ConsultarAlquileresUseCase consultarAlquileresUseCase,
                              ConsultarAlquilerDetalladoUseCase consultarAlquilerDetalldoUseCase,
                              AlquilerMapper alquilerMapper,
                              FinalizarAlquilerUseCase finalizarAlquilerUseCase) {
        this.crearAlquilerUseCase = crearAlquilerUseCase;
        this.consultarAlquileresUseCase = consultarAlquileresUseCase;
        this.consultarAlquilerDetalldoUseCase = consultarAlquilerDetalldoUseCase;
        this.alquilerMapper = alquilerMapper;
        this.finalizarAlquilerUseCase = finalizarAlquilerUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registraAlquiler(@RequestBody CrearAlquilerRequest request) {
        try {

            Alquiler alquilerCreado = crearAlquilerUseCase.crear(
                    request.clienteId(),
                    request.autoId(),
                    request.cantidadDias());
            AlquilerResponse response = alquilerMapper.toResponse(alquilerCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CantidadDiasInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse(400,
                            "Bad request", e.getMessage()));
        } catch (ClienteNoEncontradoException | AutoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404,
                            "Not Found", e.getMessage()));
        } catch (AutoNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse(409,
                            "Conflict", e.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse(400, "Bad Request", ex.getMessage()));
        }
    }

    @GetMapping("/detallados")
    public ResponseEntity<List<AlquilerDetallado>> listarAlquileresDetallados() {
            List<AlquilerDetallado> detalladoList =
                    consultarAlquilerDetalldoUseCase.listarTodosAlquilerDetallados();
            return ResponseEntity.status(HttpStatus.OK).body(detalladoList);

    }


    @GetMapping("/{id}/detallado")
    public ResponseEntity<?> obtenerDetallado(@PathVariable Long id) {
        try {
            AlquilerDetallado detalladoPorId =
                    consultarAlquilerDetalldoUseCase.consultarAlquilerDetalladoPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(detalladoPorId);
        } catch (AlquilerNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404,
                            "Not Found", e.getMessage()));
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            Alquiler alquiler = finalizarAlquilerUseCase.finalizar(id);
        return ResponseEntity.status(HttpStatus.OK).body(alquiler);
        }catch (AlquilerNoEncontradoException | AutoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404, "Not Found", e.getMessage()));
        }catch (AlquilerFinalizadoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse(409, "Conflict", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAlquilerPorId(@PathVariable Long id) {
        try {
            Alquiler alquiler = consultarAlquileresUseCase.obtenerAlquilerPorId(id);
            return ResponseEntity.ok(alquilerMapper.toResponse(alquiler));
        } catch (AlquilerNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404, "Not Found", e.getMessage()));
        }
    }

}