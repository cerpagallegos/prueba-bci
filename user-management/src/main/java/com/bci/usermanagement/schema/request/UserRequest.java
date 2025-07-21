package com.bci.usermanagement.schema.request;

import com.bci.usermanagement.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "El correo debe tener un formato válido (por ejemplo: usuario@dominio.com)"
    )
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @ValidPassword
    private String contraseña;

    @NotEmpty(message = "Debe registrar al menos un teléfono")
    private List<PhoneRequest> telefonos;

}
