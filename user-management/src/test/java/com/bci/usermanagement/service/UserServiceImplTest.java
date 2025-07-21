package com.bci.usermanagement.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private Mappers mappers;

    @Mock
    private UserMapper userMapper;

    private UserRequest userRequest;
    private Usuario usuario;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setNombre("Juan");
        userRequest.setCorreo("juan@correo.com");
        userRequest.setContraseña("Hunter123");

        userId = UUID.randomUUID();
        usuario = new Usuario();
        usuario.setId(userId);
        usuario.setCorreo(userRequest.getCorreo());
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        when(userRepository.existsByCorreo(anyString())).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("El correo ya se encuentra registrado", exception.getMessage());
    }

    @Test
    void getUserById_notFound_throwsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("El usuario no existe", exception.getMessage());
    }

    @Test
    void updateUser_success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(usuario));
        userService.updateUser(userId, userRequest);

        verify(userRepository).save(any());
    }

    @Test
    void updateUser_duplicateEmail_throwsException() {
        usuario.setCorreo("otro@correo.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(userRepository.existsByCorreo(any())).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.updateUser(userId, userRequest);
        });

        assertEquals("El correo ya se encuentra registrado", exception.getMessage());
    }

    @Test
    void patchUser_success() {
        EstadoRequest estado = new EstadoRequest(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(usuario));

        userService.patchUser(userId, estado);

        verify(userRepository).save(usuario);
        assertTrue(usuario.isActivo());
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(usuario));

        userService.deleteUser(userId);

        verify(userRepository).delete(usuario);
    }

    @Test
    void deleteUser_notFound_throwsException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Usuario no encontrado para eliminar usuario", exception.getMessage());
    }
}
