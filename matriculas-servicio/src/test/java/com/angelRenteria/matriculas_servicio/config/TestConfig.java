package com.angelRenteria.matriculas_servicio.config;

import com.angelRenteria.matriculas_servicio.client.AuthClient;
import com.angelRenteria.matriculas_servicio.dto.LoginRequest;
import com.angelRenteria.matriculas_servicio.dto.LoginResponse;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public AuthClient mockAuthClient() {
        return new AuthClient() {
            @Override
            public LoginResponse login(LoginRequest request) {
                return new LoginResponse("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmdlbEBtYWlsLmNvbSIsInJvbCI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NTA4NTM3OSwiZXhwIjoxNzQ1MDg4OTc5fQ.KWTQXAS1erhW62SOu8UYWNZHXvdoQXNYFjs2a2EjN6A");
            }
        };
    }
} 