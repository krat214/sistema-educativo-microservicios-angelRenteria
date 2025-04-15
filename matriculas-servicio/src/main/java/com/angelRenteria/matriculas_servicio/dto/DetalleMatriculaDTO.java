package com.angelRenteria.matriculas_servicio.dto;

import com.angelRenteria.matriculas_servicio.model.Usuario;
import com.angelRenteria.matriculas_servicio.model.Asignatura;
import com.angelRenteria.matriculas_servicio.model.Matricula;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMatriculaDTO {
    private Matricula matricula;
    private Usuario usuario;
    private Asignatura asignatura;
}
