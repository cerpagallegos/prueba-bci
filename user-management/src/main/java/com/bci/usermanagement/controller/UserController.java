package com.bci.usermanagement.controller;

import com.bci.usermanagement.schema.request.EstadoRequest;
import com.bci.usermanagement.schema.request.UserRequest;
import com.bci.usermanagement.schema.response.CreateUserResponse;
import com.bci.usermanagement.schema.response.UserResponse;
import com.bci.usermanagement.service.UserService;
import com.bci.usermanagement.service.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/management/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
            CreateUserResponse response = userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUserById(@RequestHeader("Authorization") String authHeader) {
            String token = authHeader.replace("Bearer ", "");
            UUID id = jwtService.extractUsuarioId(token);
            UserResponse response = userService.getUserById(id);
            return ResponseEntity.ok(response);
    }

    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestHeader("Authorization") String authHeader,
                                        @Valid @RequestBody UserRequest request) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtService.extractUsuarioId(token);
        userService.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/user/state")
    public ResponseEntity<Void> patchUser(@RequestHeader("Authorization") String authHeader,
                                          @Valid @RequestBody EstadoRequest estadoRequest) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtService.extractUsuarioId(token);
        userService.patchUser(id, estadoRequest);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UUID id = jwtService.extractUsuarioId(token);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
