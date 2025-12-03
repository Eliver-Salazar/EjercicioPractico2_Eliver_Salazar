package com.academia.plataformaAcademica.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapeamos la propiedad "nombres" a la columna "nombre"
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombres;

    // Mapeamos la propiedad "apellidos" a la columna "apellido"
    @Column(name = "apellido", nullable = false, length = 150)
    private String apellidos;

    // email → columna email (esto ya lo tenías bien)
    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    // password → columna password (también bien)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolSistema rolAsignado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
