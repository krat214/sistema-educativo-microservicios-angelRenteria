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
                // Token actualizado
                String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmdlbEBtYWlsLmNvbSIsInJvbCI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NTAzNDI3MiwiZXhwIjoxNzQ1MDM3ODcyfQ.JcT3Ut0iBOckpPb9-TVuqJG5KPOVBK8XymoZ0K_9T78";
                template.header("Authorization", token);
            }
        };
    }
}

