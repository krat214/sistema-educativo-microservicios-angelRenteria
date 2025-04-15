package com.angelRenteria.usuarios_servicio.controller;
import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService servicio;

    public UsuarioController(UsuarioService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Usuario> listar() {
        return servicio.listar();
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return servicio.guardar(usuario);
    }
}
