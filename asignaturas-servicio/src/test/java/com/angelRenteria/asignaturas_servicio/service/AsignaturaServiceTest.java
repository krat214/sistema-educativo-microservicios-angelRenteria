package com.angelRenteria.asignaturas_servicio.service;

import com.angelRenteria.asignaturas_servicio.model.Asignatura;
import com.angelRenteria.asignaturas_servicio.repository.AsignaturaRepository;
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
public class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository repository;

    @InjectMocks
    private AsignaturaService service;

    private Asignatura asignatura;

    @BeforeEach
    void setUp() {
        asignatura = Asignatura.builder()
                .id(1L)
                .nombre("Programación Avanzada")
                .descripcion("Curso de programación con patrones de diseño")
                .creditos(5)
                .build();
    }

    @Test
    void debeListarAsignaturas() {
        // Arrange
        List<Asignatura> asignaturas = Arrays.asList(asignatura);
        when(repository.findAll()).thenReturn(asignaturas);

        // Act
        List<Asignatura> resultado = service.listar();

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Programación Avanzada");
        verify(repository, times(1)).findAll();
    }

    @Test
    void debeBuscarAsignaturaPorId() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(asignatura));

        // Act
        Optional<Asignatura> resultado = service.buscarPorId(1L);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Programación Avanzada");
        assertThat(resultado.get().getCreditos()).isEqualTo(5);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void debeGuardarAsignatura() {
        // Arrange
        Asignatura nuevaAsignatura = Asignatura.builder()
                .nombre("Microservicios")
                .descripcion("Arquitectura de microservicios con Spring Boot")
                .creditos(4)
                .build();

        when(repository.save(any(Asignatura.class))).thenReturn(
                Asignatura.builder()
                        .id(2L)
                        .nombre("Microservicios")
                        .descripcion("Arquitectura de microservicios con Spring Boot")
                        .creditos(4)
                        .build()
        );

        // Act
        Asignatura resultado = service.guardar(nuevaAsignatura);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getNombre()).isEqualTo("Microservicios");
        verify(repository, times(1)).save(any(Asignatura.class));
    }

    @Test
    void debeEliminarAsignatura() {
        // Arrange
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // Act
        service.eliminar(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
} 