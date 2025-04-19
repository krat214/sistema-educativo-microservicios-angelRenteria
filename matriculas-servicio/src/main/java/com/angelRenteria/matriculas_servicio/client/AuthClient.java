package com.angelRenteria.matriculas_servicio.client;

import com.angelRenteria.matriculas_servicio.config.AuthClientConfig;
import com.angelRenteria.matriculas_servicio.dto.LoginRequest;
import com.angelRenteria.matriculas_servicio.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-client", url = "${auth.service.url:${usuarios-servicio.url:http://localhost:8001}}", path = "/auth", configuration = AuthClientConfig.class)
public interface AuthClient {
    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request);
} 