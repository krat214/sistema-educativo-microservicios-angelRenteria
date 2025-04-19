#!/bin/bash

# Script para generar un token JWT del servicio de usuarios

echo "Generando nuevo token JWT..."
echo "Asegúrate de que el servicio de usuarios esté ejecutándose en http://localhost:8001"

# Llamada a la API de login
RESPONSE=$(curl -s -X POST http://localhost:8001/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"angel@mail.com","password":"password"}')

# Extraer el token
TOKEN=$(echo $RESPONSE | grep -o '"token":"[^"]*"' | sed 's/"token":"//g' | sed 's/"//g')

if [ -z "$TOKEN" ]; then
  echo "Error: No se pudo obtener el token. Respuesta del servidor:"
  echo $RESPONSE
  exit 1
fi

echo ""
echo "==========================================================="
echo "NUEVO TOKEN JWT GENERADO:"
echo "==========================================================="
echo "Bearer $TOKEN"
echo "==========================================================="
echo ""
echo "Copia este token y actualiza la constante FIXED_TOKEN en:"
echo "com.angelRenteria.matriculas_servicio.config.FeignClientConfig.java"
echo "" 