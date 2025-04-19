package com.angelRenteria.usuarios_servicio.service;

import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nombre("Usuario Test")
                .email("test@example.com")
                .password("1234")
                .rol("ROLE_USER")
                .build();
    }

    @Test
    void debeListarUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(repository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> resultado = service.listar();

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Usuario Test");
        verify(repository, times(1)).findAll();
    }

    @Test
    void debeBuscarUsuarioPorId() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        Optional<Usuario> resultado = service.buscarPorId(1L);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Usuario Test");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void debeGuardarUsuario() {
        // Arrange
        Usuario nuevoUsuario = Usuario.builder()
                .nombre("Nuevo Usuario")
                .email("nuevo@example.com")
                .password("5678")
                .rol("ROLE_ADMIN")
                .build();

        when(repository.save(any(Usuario.class))).thenReturn(
                Usuario.builder()
                        .id(2L)
                        .nombre("Nuevo Usuario")
                        .email("nuevo@example.com")
                        .password("5678")
                        .rol("ROLE_ADMIN")
                        .build()
        );

        // Act
        Usuario resultado = service.guardar(nuevoUsuario);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getNombre()).isEqualTo("Nuevo Usuario");
        verify(repository, times(1)).save(any(Usuario.class));
    }
}

