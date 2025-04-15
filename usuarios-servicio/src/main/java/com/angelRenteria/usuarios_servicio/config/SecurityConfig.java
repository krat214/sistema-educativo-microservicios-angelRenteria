package com.angelRenteria.usuarios_servicio.config;

import com.angelRenteria.usuarios_servicio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**", "/actuator/**").permitAll() // ⬅️ Aquí el cambio
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                            throws ServletException, IOException {
                        String authHeader = request.getHeader("Authorization");
                        if (authHeader != null && authHeader.startsWith("Bearer ")) {
                            String token = authHeader.substring(7);
                            if (jwtService.validateToken(token)) {
                                String username = jwtService.extractUsername(token);
                                var auth = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            }
                        }
                        chain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
