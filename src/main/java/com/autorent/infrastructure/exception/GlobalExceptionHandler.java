package com.autorent.infrastructure.exception;

import com.autorent.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PlacaDuplicadaException.class)
    public ResponseEntity<Map<String, Object>> manejarPlacaDuplicadaException(PlacaDuplicadaException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.CONFLICT.value(),"error","conflicto"
        ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ClienteDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> ClienteDuplicadoException(ClienteDuplicadoException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.CONFLICT.value(),"error","conflicto"
                ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AutoNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> AutoNoDisponibleException(AutoNoDisponibleException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.CONFLICT.value(),"error","conflicto"
                ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AutoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> AutoNoEncontradoException(AutoNoEncontradoException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.NOT_FOUND.value(),"error","no encontrado"
                ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> ClienteNoEncontradoException(ClienteNoEncontradoException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.NOT_FOUND.value(),"error","no encontrado"
                ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlquilerNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> AlquilerNoEncontradoException(AlquilerNoEncontradoException ex) {
        Map<String, Object> response = Map.of("fecha",LocalDateTime.now().toString(),
                "estado", HttpStatus.NOT_FOUND.value(),"error","no encontrado"
                ,"mensaje",ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
