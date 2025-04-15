package com.angelRenteria.matriculas_servicio.service;


import com.angelRenteria.matriculas_servicio.client.AsignaturaClient;
import com.angelRenteria.matriculas_servicio.client.UsuarioClient;
import com.angelRenteria.matriculas_servicio.dto.DetalleMatriculaDTO;
import com.angelRenteria.matriculas_servicio.model.Asignatura;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import com.angelRenteria.matriculas_servicio.model.Usuario;
import com.angelRenteria.matriculas_servicio.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository repo;
    private final UsuarioClient usuarioClient;
    private final AsignaturaClient asignaturaClient;

    public List<Matricula> listar() {
        return repo.findAll();
    }

    public DetalleMatriculaDTO crear(Matricula matricula) {
        Usuario usuario = usuarioClient.getUsuarioPorId(matricula.getUsuarioId());
        Asignatura asignatura = asignaturaClient.getAsignaturaPorId(matricula.getAsignaturaId());
        Matricula nueva = repo.save(matricula);
        return new DetalleMatriculaDTO(nueva, usuario, asignatura);
    }

    public DetalleMatriculaDTO obtenerDetallePorId(Long id) {
        Matricula matricula = repo.findById(id).orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        Usuario usuario = usuarioClient.getUsuarioPorId(matricula.getUsuarioId());
        Asignatura asignatura = asignaturaClient.getAsignaturaPorId(matricula.getAsignaturaId());
        return new DetalleMatriculaDTO(matricula, usuario, asignatura);
    }

    public List<DetalleMatriculaDTO> listarDetalles() {
        List<Matricula> matriculas = repo.findAll();
        return matriculas.stream().map(matricula -> {
            Usuario usuario = usuarioClient.getUsuarioPorId(matricula.getUsuarioId());
            Asignatura asignatura = asignaturaClient.getAsignaturaPorId(matricula.getAsignaturaId());
            return new DetalleMatriculaDTO(matricula, usuario, asignatura);
        }).toList();
    }
}
