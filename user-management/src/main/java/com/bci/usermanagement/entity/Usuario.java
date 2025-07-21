package com.bci.usermanagement.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String correo;
    private String contraseña;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaUltimoAcceso;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos;
    private boolean activo;
}
