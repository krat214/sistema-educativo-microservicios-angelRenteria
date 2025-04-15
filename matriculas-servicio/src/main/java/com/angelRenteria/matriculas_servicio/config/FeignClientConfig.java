package com.angelRenteria.matriculas_servicio.config;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Token estático por ahora — reemplaza con uno válido de tu /auth/login
                String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmdlbEBtYWlsLmNvbSIsInJvbCI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NDY4ODg1OCwiZXhwIjoxNzQ0NjkyNDU4fQ.iANwgyC2e59_dyCPxhtn8xQpk_8jTpTHxq6TrF430lQ";
                template.header("Authorization", token);
            }
        };
    }
}

