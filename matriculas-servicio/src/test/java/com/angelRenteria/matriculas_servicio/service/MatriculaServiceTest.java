package com.angelRenteria.matriculas_servicio.service;

import com.angelRenteria.matriculas_servicio.client.AsignaturaClient;
import com.angelRenteria.matriculas_servicio.client.UsuarioClient;
import com.angelRenteria.matriculas_servicio.dto.DetalleMatriculaDTO;
import com.angelRenteria.matriculas_servicio.model.Asignatura;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import com.angelRenteria.matriculas_servicio.model.Usuario;
import com.angelRenteria.matriculas_servicio.repository.MatriculaRepository;
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
public class MatriculaServiceTest {

    @Mock
    private MatriculaRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private AsignaturaClient asignaturaClient;

    @InjectMocks
    private MatriculaService service;

    private Usuario usuario;
    private Asignatura asignatura;
    private Matricula matricula;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Estudiante Test", "estudiante@example.com", "password", "ROLE_ESTUDIANTE");

        asignatura = new Asignatura(1L, "Microservicios", "Curso de arquitectura de microservicios", "Profesor Test");

        matricula = Matricula.builder()
                .id(1L)
                .usuarioId(1L)
                .asignaturaId(1L)
                .build();
    }

    @Test
    void debeCrearMatriculaConDetalles() {
        // Arrange
        Matricula nuevaMatricula = Matricula.builder()
                .usuarioId(1L)
                .asignaturaId(1L)
                .build();

        when(repository.save(any(Matricula.class))).thenReturn(matricula);
        when(usuarioClient.getUsuarioPorId(1L)).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaPorId(1L)).thenReturn(asignatura);

        // Act
        DetalleMatriculaDTO resultado = service.crear(nuevaMatricula);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getMatricula().getId()).isEqualTo(1L);
        assertThat(resultado.getUsuario().getNombre()).isEqualTo("Estudiante Test");
        assertThat(resultado.getAsignatura().getNombre()).isEqualTo("Microservicios");
        
        verify(repository, times(1)).save(any(Matricula.class));
        verify(usuarioClient, times(1)).getUsuarioPorId(1L);
        verify(asignaturaClient, times(1)).getAsignaturaPorId(1L);
    }

    @Test
    void debeListarMatriculas() {
        // Arrange
        List<Matricula> matriculas = Arrays.asList(matricula);
        when(repository.findAll()).thenReturn(matriculas);
        when(usuarioClient.getUsuarioPorId(1L)).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaPorId(1L)).thenReturn(asignatura);

        // Act
        List<DetalleMatriculaDTO> resultado = service.listarDetalles();

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getUsuario().getNombre()).isEqualTo("Estudiante Test");
        assertThat(resultado.get(0).getAsignatura().getNombre()).isEqualTo("Microservicios");
        
        verify(repository, times(1)).findAll();
        verify(usuarioClient, times(1)).getUsuarioPorId(1L);
        verify(asignaturaClient, times(1)).getAsignaturaPorId(1L);
    }

    @Test
    void debeObtenerDetallePorId() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(matricula));
        when(usuarioClient.getUsuarioPorId(1L)).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaPorId(1L)).thenReturn(asignatura);

        // Act
        DetalleMatriculaDTO resultado = service.obtenerDetallePorId(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getMatricula().getId()).isEqualTo(1L);
        assertThat(resultado.getUsuario().getNombre()).isEqualTo("Estudiante Test");
        assertThat(resultado.getAsignatura().getNombre()).isEqualTo("Microservicios");
        
        verify(repository, times(1)).findById(1L);
        verify(usuarioClient, times(1)).getUsuarioPorId(1L);
        verify(asignaturaClient, times(1)).getAsignaturaPorId(1L);
    }
} 