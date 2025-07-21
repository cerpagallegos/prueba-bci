package com.bci.usermanagement.service;

import com.bci.usermanagement.schema.request.EstadoRequest;
import com.bci.usermanagement.schema.request.UserRequest;
import com.bci.usermanagement.schema.response.CreateUserResponse;
import com.bci.usermanagement.schema.response.UserResponse;

import java.util.UUID;

public interface UserService {
    CreateUserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(UUID id);

    void updateUser(UUID id, UserRequest userRequest);

    void patchUser(UUID id, EstadoRequest estadoRequest);

    void deleteUser(UUID id);
}
