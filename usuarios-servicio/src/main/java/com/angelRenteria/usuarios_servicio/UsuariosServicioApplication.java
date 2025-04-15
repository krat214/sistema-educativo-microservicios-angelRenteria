package com.angelRenteria.usuarios_servicio;

import com.angelRenteria.usuarios_servicio.model.Usuario;
import com.angelRenteria.usuarios_servicio.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsuariosServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosServicioApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UsuarioService usuarioService) {
		return args -> {
			Usuario u1 = new Usuario(null, "angel", "angel@mail.com", "1234", "ROLE_USER");
			usuarioService.guardar(u1);
		};
	}


}
