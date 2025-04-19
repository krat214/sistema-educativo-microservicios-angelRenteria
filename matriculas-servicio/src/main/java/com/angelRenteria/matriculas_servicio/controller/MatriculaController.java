package com.angelRenteria.matriculas_servicio.controller;


import com.angelRenteria.matriculas_servicio.dto.DetalleMatriculaDTO;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import com.angelRenteria.matriculas_servicio.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
@Tag(name = "Matrículas", description = "API para gestionar matrículas de estudiantes en asignaturas")
public class MatriculaController {

    private final MatriculaService servicio;

    @GetMapping
    @Operation(summary = "Listar todas las matrículas", description = "Obtiene la lista de todas las matrículas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de matrículas obtenida exitosamente")
    public List<Matricula> listar() {
        return servicio.listar();
    }

    @PostMapping
    @Operation(summary = "Crear una matrícula", description = "Registra una nueva matrícula de un estudiante en una asignatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Matrícula creada exitosamente",
                    content = @Content(schema = @Schema(implementation = DetalleMatriculaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario o asignatura no encontrados")
    })
    public ResponseEntity<DetalleMatriculaDTO> crear(@RequestBody Matricula matricula) {
        DetalleMatriculaDTO dto = servicio.crear(matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de matrícula", description = "Obtiene el detalle completo de una matrícula incluyendo información del estudiante y la asignatura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de matrícula obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = DetalleMatriculaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Matrícula no encontrada")
    })
    public ResponseEntity<DetalleMatriculaDTO> obtenerDetalle(
            @Parameter(description = "ID de la matrícula a consultar", required = true)
            @PathVariable Long id) {
        DetalleMatriculaDTO detalle = servicio.obtenerDetallePorId(id);
        return ResponseEntity.ok(detalle);
    }

    @GetMapping("/detalles")
    @Operation(summary = "Listar todos los detalles de matrículas", description = "Obtiene la lista completa de matrículas con detalles de estudiantes y asignaturas")
    @ApiResponse(responseCode = "200", description = "Lista de detalles obtenida exitosamente")
    public List<DetalleMatriculaDTO> listarDetalles() {
        return servicio.listarDetalles();
    }

}

