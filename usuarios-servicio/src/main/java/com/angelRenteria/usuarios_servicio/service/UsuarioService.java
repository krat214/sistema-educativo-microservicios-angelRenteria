package com.angelRenteria.usuarios_servicio.service;
import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario guardar(Usuario u) {
        return repo.save(u);
    }

    public Usuario obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

}

