package com.angelRenteria.matriculas_servicio;

import com.angelRenteria.matriculas_servicio.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class MatriculasServicioApplicationTests {

	@Test
	void contextLoads() {
	}

}
