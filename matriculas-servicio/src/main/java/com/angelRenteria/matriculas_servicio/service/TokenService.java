package com.angelRenteria.matriculas_servicio.service;

import com.angelRenteria.matriculas_servicio.client.AuthClient;
import com.angelRenteria.matriculas_servicio.dto.LoginRequest;
import com.angelRenteria.matriculas_servicio.dto.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final AuthClient authClient;
    
    @Value("${jwt.secret:clave-super-secreta-muy-larga-para-demo-1234567890}")
    private String secretKey;
    
    // Token fijo de respaldo (fallback) que se usará cuando el servicio de autenticación no esté disponible
    private static final String FALLBACK_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmdlbEBtYWlsLmNvbSIsInJvbCI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NTA4NTM3OSwiZXhwIjoxNzQ1MDg4OTc5fQ.KWTQXAS1erhW62SOu8UYWNZHXvdoQXNYFjs2a2EjN6A";
    
    private String cachedToken;
    private long expirationTime;
    private boolean usingFallback = false;
    
    public String getToken() {
        if (cachedToken == null || isTokenExpired()) {
            refreshToken();
        }
        return cachedToken;
    }
    
    private boolean isTokenExpired() {
        // Si estamos en modo fallback, consideramos que el token nunca expira
        // para evitar reintentos constantes al servicio caído
        if (usingFallback) {
            return false;
        }
        return System.currentTimeMillis() > expirationTime - 60000; // Renovar 1 minuto antes de que expire
    }
    
    private void refreshToken() {
        // Si ya tuvimos un error previo y estamos usando el token de fallback, 
        // solo reintentamos conectarnos al servicio cada 30 segundos
        if (usingFallback && cachedToken != null) {
            if (System.currentTimeMillis() < expirationTime) {
                return; // Sigue usando el token de fallback
            }
        }
        
        // Credenciales fijas - en un entorno real deberían obtenerse de forma segura
        LoginRequest loginRequest = new LoginRequest("angel@mail.com", "password");
        
        try {
            log.info("Intentando obtener token desde el servicio de autenticación");
            LoginResponse response = authClient.login(loginRequest);
            String rawToken = response.getToken();
            
            // El token ya podría venir con el prefijo "Bearer ", asegurarnos de manejarlo correctamente
            cachedToken = rawToken.startsWith("Bearer ") ? rawToken.substring(7) : rawToken;
            
            // Extraer la fecha de expiración del token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(cachedToken)
                    .getBody();
                    
            expirationTime = claims.getExpiration().getTime();
            usingFallback = false;
            log.info("Token obtenido correctamente del servicio de autenticación");
        } catch (Exception e) {
            log.error("Error al obtener token del servicio de autenticación: " + e.getMessage());
            // Si ya teníamos un token, mantenerlo por un tiempo más
            if (cachedToken != null) {
                // Extender el tiempo del token actual
                expirationTime = System.currentTimeMillis() + 30000; // 30 segundos más
                usingFallback = true;
                log.info("Usando token existente como fallback por 30 segundos más");
            } else {
                // Usar el token de fallback
                cachedToken = FALLBACK_TOKEN;
                expirationTime = System.currentTimeMillis() + 30000; // 30 segundos
                usingFallback = true;
                log.info("No hay token previo, usando token hardcodeado como fallback");
            }
        }
    }
    
    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
} 