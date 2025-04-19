package com.angelRenteria.matriculas_servicio.client;

import com.angelRenteria.matriculas_servicio.config.FeignClientConfig;
import com.angelRenteria.matriculas_servicio.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-servicio", configuration = FeignClientConfig.class)
public interface UsuarioClient {
    @GetMapping("/usuarios/{id}")
    Usuario getUsuarioPorId(@PathVariable Long id);
}

