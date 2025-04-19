#!/bin/bash

echo "====================================================="
echo "  PRUEBAS DE SEGURIDAD DEL SISTEMA EDUCATIVO"
echo "====================================================="

# Asegurarse de que todos los servicios están funcionando
echo "\n✅ Verificando que los servicios estén activos..."
EUREKA=$(curl -s http://localhost:8761/actuator/health | grep "UP")
if [[ $EUREKA == *"UP"* ]]; then
    echo "   Eureka: UP ✓"
else
    echo "   ❌ ERROR: Eureka no está funcionando"
    exit 1
fi

echo "\n======= PRUEBAS DE AUTENTICACIÓN ======="

# 1. Intento de acceso sin token (debe fallar)
echo "\n1. Intentando acceder sin token (debe fallar):"
NO_AUTH_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/usuarios)
if [[ $NO_AUTH_RESPONSE == "401" || $NO_AUTH_RESPONSE == "403" ]]; then
    echo "   ✅ Prueba exitosa: Recibió $NO_AUTH_RESPONSE como se esperaba"
else
    echo "   ❌ Prueba fallida: Recibió $NO_AUTH_RESPONSE, debería ser 401 o 403"
fi

# 2. Obtener token válido
echo "\n2. Obteniendo token de autenticación:"
TOKEN_RESPONSE=$(curl -s -X POST http://localhost:8081/auth/login \
    -H "Content-Type: application/json" \
    -d '{"email":"angel@mail.com","password":"1234"}')

TOKEN=$(echo $TOKEN_RESPONSE | grep -o '"token":"[^"]*"' | sed 's/"token":"//g' | sed 's/"//g')

if [[ ! -z "$TOKEN" ]]; then
    echo "   ✅ Token obtenido correctamente"
    echo "   Token: ${TOKEN:0:20}... (truncado)"
else
    echo "   ❌ Error obteniendo token: $TOKEN_RESPONSE"
    exit 1
fi

# 3. Acceso con token (debe funcionar)
echo "\n3. Accediendo con token válido:"
AUTH_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -H "Authorization: Bearer $TOKEN" http://localhost:8081/usuarios)
if [[ $AUTH_RESPONSE == "200" ]]; then
    echo "   ✅ Prueba exitosa: Acceso permitido con token válido"
else
    echo "   ❌ Prueba fallida: Recibió $AUTH_RESPONSE, debería ser 200"
fi

# 4. Acceso a servicios protegidos
echo "\n4. Comprobando funcionamiento de Feign Client y propagación de tokens:"
MATRICULA_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -H "Authorization: Bearer $TOKEN" \
    -X POST http://localhost:8083/matriculas \
    -H "Content-Type: application/json" \
    -d '{"usuarioId": 1, "asignaturaId": 1}')

if [[ $MATRICULA_RESPONSE == "201" ]]; then
    echo "   ✅ Prueba exitosa: Matrícula creada correctamente con propagación de token"
else
    echo "   ❌ Prueba fallida: Recibió $MATRICULA_RESPONSE, debería ser 201"
fi

# 5. Monitoreo y salud
echo "\n======= PRUEBAS DE MONITOREO ======="
echo "\n5. Verificando endpoints de Actuator:"

HEALTH_MATRICULAS=$(curl -s http://localhost:8083/actuator/health | grep "UP")
if [[ $HEALTH_MATRICULAS == *"UP"* ]]; then
    echo "   ✅ Health check de matrículas: OK"
else
    echo "   ❌ Health check de matrículas: Error"
fi

HEALTH_USUARIOS=$(curl -s http://localhost:8081/actuator/health | grep "UP")
if [[ $HEALTH_USUARIOS == *"UP"* ]]; then
    echo "   ✅ Health check de usuarios: OK"
else
    echo "   ❌ Health check de usuarios: Error"
fi

HEALTH_ASIGNATURAS=$(curl -s http://localhost:8082/actuator/health | grep "UP")
if [[ $HEALTH_ASIGNATURAS == *"UP"* ]]; then
    echo "   ✅ Health check de asignaturas: OK"
else
    echo "   ❌ Health check de asignaturas: Error"
fi

echo "\n====================================================="
echo "            PRUEBAS DE SEGURIDAD COMPLETADAS"
echo "=====================================================" 