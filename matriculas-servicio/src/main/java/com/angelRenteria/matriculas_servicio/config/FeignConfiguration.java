package com.angelRenteria.matriculas_servicio.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.angelRenteria.matriculas_servicio.client")
@ConditionalOnProperty(name = "spring.cloud.openfeign.enabled", havingValue = "true", matchIfMissing = true)
public class FeignConfiguration {
    // Esta clase configura condicionalmente los clientes Feign
} 