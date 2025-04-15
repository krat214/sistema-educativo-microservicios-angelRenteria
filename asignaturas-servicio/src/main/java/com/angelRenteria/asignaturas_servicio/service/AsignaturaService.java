package com.angelRenteria.asignaturas_servicio.service;


import com.angelRenteria.asignaturas_servicio.model.Asignatura;
import com.angelRenteria.asignaturas_servicio.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignaturaService {

    private final AsignaturaRepository repo;

    public List<Asignatura> listar() {
        return repo.findAll();
    }

    public Optional<Asignatura> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Asignatura guardar(Asignatura a) {
        return repo.save(a);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}

