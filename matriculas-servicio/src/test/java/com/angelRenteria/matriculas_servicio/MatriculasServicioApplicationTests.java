package com.angelRenteria.matriculas_servicio;

import com.angelRenteria.matriculas_servicio.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
class MatriculasServicioApplicationTests {

	@Test
	void contextLoads() {
	}

}
