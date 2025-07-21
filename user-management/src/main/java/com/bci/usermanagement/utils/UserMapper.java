package com.bci.usermanagement.utils;

import com.bci.usermanagement.entity.Telefono;
import com.bci.usermanagement.entity.Usuario;
import com.bci.usermanagement.schema.request.UserRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static Usuario mapToUser(UserRequest request) {
        Usuario user = new Usuario();
        user.setNombre(request.getNombre());
        user.setCorreo(request.getCorreo());
        user.setContraseña(request.getContraseña());
        user.setTelefonos(mapToPhones(request, user));
        user.setFechaModificacion(LocalDateTime.now());
        return user;
    }

    public static void updateUserFromRequest(Usuario user, UserRequest request) {
        user.setNombre(request.getNombre());
        user.setCorreo(request.getCorreo());
        user.setContraseña(request.getContraseña());
        if (request.getTelefonos() != null) {
            request.getTelefonos().clear();
            user.getTelefonos().addAll(mapToPhones(request, user));
        }
        user.setFechaModificacion(LocalDateTime.now());
    }

    private static List<Telefono> mapToPhones(UserRequest request, Usuario user) {
        return request.getTelefonos().stream().map(telReq -> {
            Telefono tel = new Telefono();
            tel.setNumero(telReq.getNumero());
            tel.setCodigoCiudad(telReq.getCodigoCiudad());
            tel.setCodigoPais(telReq.getCodigoPais());
            tel.setUsuario(user);
            return tel;
        }).collect(Collectors.toList());
    }

}
