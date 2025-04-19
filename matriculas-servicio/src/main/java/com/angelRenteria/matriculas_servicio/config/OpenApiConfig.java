package com.angelRenteria.matriculas_servicio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("http://localhost:8083" + contextPath)
                                .description("Local Server"),
                        new Server().url("http://matriculas-servicio:8083" + contextPath)
                                .description("Docker Server")
                ))
                .info(new Info()
                        .title("API de Matrículas")
                        .description("API para gestionar las matrículas de estudiantes en asignaturas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Angel Renteria")
                                .email("angel@mail.com")
                                .url("https://github.com/angelRenteria"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
} 