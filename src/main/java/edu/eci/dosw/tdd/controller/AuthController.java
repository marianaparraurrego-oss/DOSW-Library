package edu.eci.dosw.tdd.controller;

import edu.eci.dosw.tdd.controller.dto.LoginDTO;
import edu.eci.dosw.tdd.controller.dto.TokenDTO;
import edu.eci.dosw.tdd.core.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Login y obtención de token JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Iniciar sesión y obtener token JWT")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        String token = authService.login(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}