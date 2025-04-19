package com.angelRenteria.matriculas_servicio.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utilidad para generar un token JWT desde el servicio de usuarios.
 * Ejecuta esta clase como aplicación Java cuando necesites actualizar el token 
 * en FeignClientConfig.java.
 */
public class GenerarToken {

    // URL del servicio de autenticación
    private static final String AUTH_URL = "http://localhost:8001/auth/login";
    
    public static void main(String[] args) {
        try {
            // Crear conexión HTTP
            URL url = new URL(AUTH_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // Crear cuerpo de la petición
            String jsonInputString = "{\"email\": \"angel@mail.com\", \"password\": \"password\"}";
            
            // Enviar la petición
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            // Leer la respuesta
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                
                // Extraer el token del JSON
                String responseStr = response.toString();
                int tokenStart = responseStr.indexOf("\"token\":\"") + 9;
                int tokenEnd = responseStr.indexOf("\"", tokenStart);
                String token = responseStr.substring(tokenStart, tokenEnd);
                
                System.out.println("\n==============================================");
                System.out.println("NUEVO TOKEN JWT GENERADO:");
                System.out.println("==============================================");
                System.out.println("Bearer " + token);
                System.out.println("==============================================");
                System.out.println("\nCopia este token y actualiza la constante FIXED_TOKEN en");
                System.out.println("com.angelRenteria.matriculas_servicio.config.FeignClientConfig.java");
            }
            
        } catch (Exception e) {
            System.out.println("Error al generar token: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 