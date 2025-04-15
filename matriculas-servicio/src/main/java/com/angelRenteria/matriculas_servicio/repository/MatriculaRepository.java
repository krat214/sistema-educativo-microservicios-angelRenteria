package com.angelRenteria.matriculas_servicio.repository;


import com.angelRenteria.matriculas_servicio.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}
