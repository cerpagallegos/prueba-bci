package com.bci.usermanagement.schema.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneRequest {

    @NotBlank(message = "El número es obligatorio")
    private String numero;

    @NotBlank(message = "El código de ciudad es obligatorio")
    private String codigoCiudad;

    @NotBlank(message = "El código de país es obligatorio")
    private String codigoPais;
}
