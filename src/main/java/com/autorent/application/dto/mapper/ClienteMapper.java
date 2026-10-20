package com.autorent.application.dto.mapper;

import com.autorent.application.dto.request.CrearClienteRequest;
import com.autorent.application.dto.response.ClienteResponse;
import com.autorent.domain.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDocumento(),
                cliente.getEmail(),
                cliente.getTelefono()
        );

    }

    public Cliente toDomain(CrearClienteRequest request) {
         return Cliente.builder()
                 .nombre(request.nombre())
                 .documento(request.documento())
                 .email(request.email())
                 .telefono(request.telefono())
                 .build();
    }
}
