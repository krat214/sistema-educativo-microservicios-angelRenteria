package com.angelRenteria.matriculas_servicio.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationConfig implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> propertySource = new HashMap<>();
        // Permitir sobreescritura de beans
        propertySource.put("spring.main.allow-bean-definition-overriding", true);
        
        MapPropertySource mapPropertySource = new MapPropertySource("applicationConfig", propertySource);
        environment.getPropertySources().addFirst(mapPropertySource);
    }
} 