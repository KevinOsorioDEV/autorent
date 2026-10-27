package com.autorent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;

    public void actualizarDatos(Cliente datos) {
        nombre = datos.getNombre();
        documento = datos.getDocumento();
        email = datos.getEmail();
        telefono = datos.getTelefono();
    }

}
