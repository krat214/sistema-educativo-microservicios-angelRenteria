package com.angelRenteria.matriculas_servicio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {FeignAutoConfiguration.class})
public class MatriculasServicioWithoutFeignTest {

    @Test
    void contextLoadsWithoutFeign() {
        // Test simple para asegurar que la aplicación carga sin clientes Feign
    }
} 