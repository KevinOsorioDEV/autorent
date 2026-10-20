package com.autorent.infrastructure.adapter.input.rest;

import com.autorent.application.dto.mapper.AlquilerDetalladoMapper;
import com.autorent.application.dto.response.AlquilerDetalladoResponse;
import com.autorent.application.dto.mapper.AlquilerMapper;
import com.autorent.application.dto.request.CrearAlquilerRequest;
import com.autorent.application.dto.response.AlquilerResponse;
import com.autorent.application.dto.response.ApiErrorResponse;
import com.autorent.application.port.input.ConsultarAlquilerDetalldoUseCase;
import com.autorent.application.port.input.ConsultarAlquileresUseCase;
import com.autorent.application.port.input.CrearAlquilerUseCase;
import com.autorent.domain.exception.*;
import com.autorent.domain.model.Alquiler;
import com.autorent.infrastructure.adapter.output.persistence.dto.AlquilerDetalladoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/alquiler")
public class AlquilerController {
    private final CrearAlquilerUseCase crearAlquilerUseCase;
    private final ConsultarAlquileresUseCase consultarAlquileresUseCase;
    private final ConsultarAlquilerDetalldoUseCase consultarAlquilerDetalldoUseCase;
    private final AlquilerMapper alquilerMapper;
    private final AlquilerDetalladoMapper alquilerDetalladoMapper;

    public AlquilerController(CrearAlquilerUseCase crearAlquilerUseCase,
                              ConsultarAlquileresUseCase consultarAlquileresUseCase, ConsultarAlquilerDetalldoUseCase consultarAlquilerDetalldoUseCase,
                              AlquilerMapper alquilerMapper,
                              AlquilerDetalladoMapper alquilerDetalladoMapper) {
        this.crearAlquilerUseCase = crearAlquilerUseCase;
        this.consultarAlquileresUseCase = consultarAlquileresUseCase;
        this.consultarAlquilerDetalldoUseCase = consultarAlquilerDetalldoUseCase;
        this.alquilerMapper = alquilerMapper;
        this.alquilerDetalladoMapper = alquilerDetalladoMapper;
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
    public ResponseEntity<List<AlquilerDetalladoResponse>> listarAlquileresDetallados() {
            List<AlquilerDetalladoResponse> detalladoList =
                    consultarAlquilerDetalldoUseCase.listarTodosAlquilerDetallados();
            return ResponseEntity.status(HttpStatus.OK).body(detalladoList);

    }


    @GetMapping("/{id}/detallado")
    public ResponseEntity<?> obtenerDetallado(@PathVariable Long id) {
        try {
            AlquilerDetalladoResponse detalladoPorId =
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
        Alquiler alquiler = consultarAlquileresUseCase.finalizar(id);
        AlquilerResponse response = alquilerMapper.toResponse(alquiler);
        return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (AlquilerNoEncontradoException | AutoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(404, "Not Found", e.getMessage()));
        }catch (AlquilerFinalizadoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse(409, "Conflict", e.getMessage()));
        }
    }


}