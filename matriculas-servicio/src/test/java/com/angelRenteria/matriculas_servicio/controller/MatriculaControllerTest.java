package com.angelRenteria.matriculas_servicio.controller;

import com.angelRenteria.matriculas_servicio.config.TestConfig;
import com.angelRenteria.matriculas_servicio.dto.DetalleMatriculaDTO;
import com.angelRenteria.matriculas_servicio.model.Asignatura;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import com.angelRenteria.matriculas_servicio.model.Usuario;
import com.angelRenteria.matriculas_servicio.service.MatriculaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatriculaController.class)
@Import(TestConfig.class)
public class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService matriculaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarMatriculasTest() throws Exception {
        // Preparar datos de prueba
        List<Matricula> matriculas = Arrays.asList(
                new Matricula(1L, 1L, 1L),
                new Matricula(2L, 1L, 2L)
        );
        
        // Configurar comportamiento del mock
        when(matriculaService.listar()).thenReturn(matriculas);
        
        // Ejecutar y verificar
        mockMvc.perform(get("/matriculas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    
    @Test
    public void crearMatriculaTest() throws Exception {
        // Preparar datos de prueba
        Matricula matricula = new Matricula(null, 1L, 1L);
        Matricula matriculaCreada = new Matricula(1L, 1L, 1L);
        
        Usuario usuario = new Usuario(1L, "Angel Rentería", "angel@mail.com", "password", "ROLE_USER");
        Asignatura asignatura = new Asignatura(1L, "Microservicios", "Curso de microservicios con Spring Boot", "Dr. Profesor");
        
        DetalleMatriculaDTO detalleDTO = new DetalleMatriculaDTO();
        detalleDTO.setMatricula(matriculaCreada);
        detalleDTO.setUsuario(usuario);
        detalleDTO.setAsignatura(asignatura);
        
        // Configurar comportamiento del mock
        when(matriculaService.crear(any(Matricula.class))).thenReturn(detalleDTO);
        
        // Ejecutar y verificar
        mockMvc.perform(post("/matriculas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.matricula.id").value(1))
                .andExpect(jsonPath("$.usuario.nombre").value("Angel Rentería"))
                .andExpect(jsonPath("$.asignatura.nombre").value("Microservicios"));
    }
    
    @Test
    public void obtenerDetalleMatriculaTest() throws Exception {
        // Preparar datos de prueba
        Matricula matricula = new Matricula(1L, 1L, 1L);
        Usuario usuario = new Usuario(1L, "Angel Rentería", "angel@mail.com", "password", "ROLE_USER");
        Asignatura asignatura = new Asignatura(1L, "Microservicios", "Curso de microservicios con Spring Boot", "Dr. Profesor");
        
        DetalleMatriculaDTO detalleDTO = new DetalleMatriculaDTO();
        detalleDTO.setMatricula(matricula);
        detalleDTO.setUsuario(usuario);
        detalleDTO.setAsignatura(asignatura);
        
        // Configurar comportamiento del mock
        when(matriculaService.obtenerDetallePorId(1L)).thenReturn(detalleDTO);
        
        // Ejecutar y verificar
        mockMvc.perform(get("/matriculas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricula.id").value(1))
                .andExpect(jsonPath("$.usuario.nombre").value("Angel Rentería"))
                .andExpect(jsonPath("$.asignatura.nombre").value("Microservicios"));
    }
} 