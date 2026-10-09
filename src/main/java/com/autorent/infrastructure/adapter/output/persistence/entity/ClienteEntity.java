package com.autorent.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false, unique = true)
    private String documento;

    @Column(length = 250, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String telefono;

}
