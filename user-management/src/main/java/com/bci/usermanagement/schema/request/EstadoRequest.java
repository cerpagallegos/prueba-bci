package com.bci.usermanagement.schema.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadoRequest {
    @NotNull(message = "El estado no puede ser nulo")
    private Boolean activo;
}