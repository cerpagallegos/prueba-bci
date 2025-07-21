package com.bci.usermanagement.utils;

import com.bci.usermanagement.entity.Telefono;
import com.bci.usermanagement.entity.Usuario;
import com.bci.usermanagement.schema.request.UserRequest;
import com.bci.usermanagement.schema.response.CreateUserResponse;
import com.bci.usermanagement.schema.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;

public class Mappers {
    public static Usuario mapToUserCreate(UserRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContraseña(request.getContraseña());
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaUltimoAcceso(LocalDateTime.now());
        usuario.setActivo(Boolean.TRUE);
        if (request.getTelefonos() != null) {
            List<Telefono> telefonos = request.getTelefonos().stream()
                    .map(telefonoReq -> {
                        Telefono telefono = new Telefono();
                        telefono.setNumero(telefonoReq.getNumero());
                        telefono.setCodigoCiudad(telefonoReq.getCodigoCiudad());
                        telefono.setCodigoPais(telefonoReq.getCodigoPais());
                        return telefono;
                    })
                    .toList();
            usuario.setTelefonos(telefonos);
        }
        return usuario;
    }
    public static BiFunction<Usuario, String, CreateUserResponse> mapToUserResponse = (usuario, token) -> {
        CreateUserResponse response = new CreateUserResponse();
        response.setId(usuario.getId());
        response.setCreado(usuario.getFechaCreacion());
        response.setModificado(usuario.getFechaModificacion());
        response.setUltimoLogin(usuario.getFechaUltimoAcceso());
        response.setToken(token);
        response.setActivo(usuario.isActivo());
        return response;
    };
    public static UserResponse mapToUserResponseWithoutToken(Usuario usuario) {
        UserResponse response = new UserResponse();
        response.setId(usuario.getId());
        response.setCreado(usuario.getFechaCreacion());
        response.setModificado(usuario.getFechaModificacion());
        response.setUltimoLogin(usuario.getFechaUltimoAcceso());
        response.setActivo(usuario.isActivo());
        response.setCorreo(usuario.getCorreo());
        response.setNombre(usuario.getNombre());
        return response;
    }
}
