package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.controller.mapper.UserMapper;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Registro y consulta de usuarios")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @Operation(summary = "Registrar un usuario nuevo")
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody UserDTO dto) {
        User user = UserMapper.toModel(dto);
        service.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> result = service.getAllUsers()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Obtener un usuario por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        User user = service.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}