package com.angelRenteria.matriculas_servicio.config;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    // Token fijo actualizado (vigente)
    private static final String FIXED_TOKEN = "Bearer eyJzdWIiOiJhbmdlbEBtYWlsLmNvbSIsInJvbCI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NTA4NTM3OSwiZXhwIjoxNzQ1MDg4OTc5fQ.KWTQXAS1erhW62SOu8UYWNZHXvdoQXNYFjs2a2EjN6A";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Aplicar el token fijo a todas las peticiones
                template.header("Authorization", FIXED_TOKEN);
            }
        };
    }
}

