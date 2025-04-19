package com.angelRenteria.matriculas_servicio.client;

import com.angelRenteria.matriculas_servicio.config.FeignClientConfig;
import com.angelRenteria.matriculas_servicio.model.Asignatura;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "asignaturas-servicio", configuration = FeignClientConfig.class)
public interface AsignaturaClient {
    @GetMapping("/asignaturas/{id}")
    Asignatura getAsignaturaPorId(@PathVariable Long id);
}
