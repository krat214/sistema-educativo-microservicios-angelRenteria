package com.angelRenteria.matriculas_servicio.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asignatura {
    private Long id;
    private String nombre;
    private String descripcion;
    private String docente;
}
