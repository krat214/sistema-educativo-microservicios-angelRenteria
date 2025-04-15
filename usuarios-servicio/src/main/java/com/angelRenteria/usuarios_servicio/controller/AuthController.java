package com.angelRenteria.usuarios_servicio.controller;

import com.angelRenteria.usuarios_servicio.dto.LoginRequest;
import com.angelRenteria.usuarios_servicio.dto.LoginResponse;
import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.repository.UsuarioRepository;
import com.angelRenteria.usuarios_servicio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();
        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}

