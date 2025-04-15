package com.angelRenteria.usuarios_servicio.service;

import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository repository;
    private UsuarioService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(UsuarioRepository.class);
        service = new UsuarioService(repository);
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "1234", "ROLE_USER");
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario result = service.guardar(usuario);

        assertEquals("Juan", result.getNombre());
        verify(repository).save(usuario);
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> usuarios = List.of(
                new Usuario(1L, "Ana", "ana@mail.com", "pass", "ROLE_USER"),
                new Usuario(2L, "Luis", "luis@mail.com", "pass", "ROLE_ADMIN")
        );

        when(repository.findAll()).thenReturn(usuarios);

        List<Usuario> result = service.listar();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testObtenerUsuarioPorId() {
        Usuario usuario = new Usuario(1L, "Carlos", "carlos@mail.com", "clave", "ROLE_USER");
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = service.obtenerPorId(1L);

        assertEquals("Carlos", result.getNombre());
        verify(repository).findById(1L);
    }
}

