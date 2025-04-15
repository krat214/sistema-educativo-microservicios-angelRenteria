package com.angelRenteria.asignaturas_servicio.controller;

import com.angelRenteria.asignaturas_servicio.model.Asignatura;
import com.angelRenteria.asignaturas_servicio.service.AsignaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService servicio;

    @GetMapping
    public List<Asignatura> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> buscar(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Asignatura crear(@RequestBody Asignatura a) {
        return servicio.guardar(a);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> actualizar(@PathVariable Long id, @RequestBody Asignatura a) {
        return servicio.buscarPorId(id).map(asignatura -> {
            a.setId(id);
            return ResponseEntity.ok(servicio.guardar(a));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

