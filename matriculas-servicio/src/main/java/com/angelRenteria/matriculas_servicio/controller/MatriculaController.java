package com.angelRenteria.matriculas_servicio.controller;


import com.angelRenteria.matriculas_servicio.dto.DetalleMatriculaDTO;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import com.angelRenteria.matriculas_servicio.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService servicio;

    @GetMapping
    public List<Matricula> listar() {
        return servicio.listar();
    }

    @PostMapping
    public ResponseEntity<DetalleMatriculaDTO> crear(@RequestBody Matricula matricula) {
        DetalleMatriculaDTO dto = servicio.crear(matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleMatriculaDTO> obtenerDetalle(@PathVariable Long id) {
        DetalleMatriculaDTO detalle = servicio.obtenerDetallePorId(id);
        return ResponseEntity.ok(detalle);
    }

    @GetMapping("/detalles")
    public List<DetalleMatriculaDTO> listarDetalles() {
        return servicio.listarDetalles();
    }

}

