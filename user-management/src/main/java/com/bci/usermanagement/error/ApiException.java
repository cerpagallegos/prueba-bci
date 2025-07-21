package com.bci.usermanagement.error;

public class ApiException extends RuntimeException {
    public ApiException(String mensaje) {
        super(mensaje);
    }
}
