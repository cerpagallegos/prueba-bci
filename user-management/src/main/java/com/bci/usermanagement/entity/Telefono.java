package com.bci.usermanagement.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import java.util.UUID;

@Entity
@Data
public class Telefono {

    @Id
    @GeneratedValue
    private UUID id;
    private String numero;
    private String codigoCiudad;
    private String codigoPais;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}