package com.bci.usermanagement.service;

import com.bci.usermanagement.entity.Telefono;
import com.bci.usermanagement.entity.Usuario;
import com.bci.usermanagement.error.ApiException;
import com.bci.usermanagement.repository.UserRepository;
import com.bci.usermanagement.schema.request.EstadoRequest;
import com.bci.usermanagement.schema.request.UserRequest;
import com.bci.usermanagement.schema.response.CreateUserResponse;
import com.bci.usermanagement.schema.response.UserResponse;
import com.bci.usermanagement.service.jwt.JwtService;
import com.bci.usermanagement.utils.Mappers;
import com.bci.usermanagement.utils.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtService jwtService;

    @Override
    public CreateUserResponse createUser(UserRequest request) {
        if (userRepository.existsByCorreo(request.getCorreo())) {
            throw new ApiException("El correo ya se encuentra registrado");
        }

        Usuario user =  Mappers.mapToUserCreate(request);
        Usuario userSave  = userRepository.save(user);
        String jwtToken = jwtService.generateToken(userSave);
        return Mappers.mapToUserResponse.apply(userSave, jwtToken);
    }

    @Override
    public UserResponse getUserById(UUID id) {
       Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("El usuario no existe"));
       return Mappers.mapToUserResponseWithoutToken(user);
    }

    @Override
    public void updateUser(UUID id, UserRequest request) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("Usuario no encontrado para actualizar"));

        if (!user.getCorreo().equals(request.getCorreo()) &&
                userRepository.existsByCorreo(request.getCorreo())) {
            throw new ApiException("El correo ya se encuentra registrado");
        }

        UserMapper.updateUserFromRequest(user, request);
        userRepository.save(user);
    }

    @Override
    public void patchUser(UUID id, EstadoRequest estadoRequest) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("Usuario no encontrado para actulizar estado"));
        user.setActivo(estadoRequest.getActivo());
        user.setFechaModificacion(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("Usuario no encontrado para eliminar usuario"));
        userRepository.delete(user);
    }

}
