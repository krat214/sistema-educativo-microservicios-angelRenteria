package com.angelRenteria.matriculas_servicio;

import com.angelRenteria.matriculas_servicio.client.AuthClient;
import com.angelRenteria.matriculas_servicio.dto.LoginRequest;
import com.angelRenteria.matriculas_servicio.dto.LoginResponse;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockAuthConfig {
    
    @Bean
    @Primary
    public AuthClient authClient() {
        return new AuthClient() {
            @Override
            public LoginResponse login(LoginRequest request) {
                return new LoginResponse("mock-token-for-testing");
            }
        };
    }
} 