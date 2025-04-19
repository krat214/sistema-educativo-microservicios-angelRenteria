package com.angelRenteria.matriculas_servicio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cloud.openfeign.enabled=false",
        "spring.cloud.config.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false"
})
class MatriculasServicioApplicationTests {

	@Test
	void contextLoads() {
		// Test simple para verificar que el contexto de Spring se carga correctamente
	}

}
