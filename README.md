# 📚 Sistema Educativo - Microservicios

Proyecto desarrollado para el parcial 2 de Microservicios.  
Contiene los servicios de usuarios, asignaturas y matrículas, comunicados entre sí mediante Eureka, Config Server y Feign Client.

---

## 🧱 Microservicios

| Microservicio         | Puerto | Descripción                                     |
|-----------------------|--------|-------------------------------------------------|
| `usuarios-servicio`   | 8081   | Gestión de usuarios y autenticación JWT         |
| `asignaturas-servicio`| 8082   | Gestión de asignaturas                          |
| `matriculas-servicio` | 8083   | Gestión de matrículas e integración entre servicios |

---

## ⚙️ Configuración e Integración

### 🗂️ Config Server

- Todos los servicios están configurados para cargar sus propiedades desde un **repositorio remoto en GitHub**.
- Las propiedades están organizadas por nombre del microservicio:  
  Ejemplo:  
  `usuarios-servicio.properties`, `asignaturas-servicio.properties`, etc.

### ☁️ Eureka Server

- Los microservicios se registran automáticamente en Eureka como clientes.
- Esto permite descubrir servicios por nombre lógico (Ej: `usuarios-servicio`) en lugar de usar URLs fijas.

### 🔁 Comunicación entre microservicios

- Se utiliza **Feign Client** para que los servicios puedan llamarse entre sí.
- `matriculas-servicio` se comunica con:
  - `usuarios-servicio` (para obtener información del usuario)
  - `asignaturas-servicio` (para obtener información de la asignatura)

```java
@FeignClient(name = "usuarios-servicio")
public interface UsuarioClient {
    @GetMapping("/usuarios/{id}")
    Usuario getUsuarioPorId(@PathVariable Long id);
}
```

---

## 🔐 Seguridad

- `usuarios-servicio` implementa autenticación con **JWT (JSON Web Token)**.
- Se generan tokens de acceso tras el login.
- Otros servicios pueden validar tokens para proteger rutas privadas (en progreso).

---

## 🔍 Monitoreo

- Todos los servicios exponen endpoints vía **Spring Boot Actuator**.
- Se puede acceder a información de salud, beans, rutas, etc., desde `/actuator`.

---

## 🧪 Base de Datos

- Cada microservicio trabaja con **H2 en memoria** para pruebas locales.
- No se persiste información entre reinicios.

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio:  
   `git clone https://github.com/usuario/sistema-educativo-microservicios.git`

2. Iniciar el servidor de configuración y Eureka.

3. Ejecutar cada microservicio desde su carpeta con:

```bash
./gradlew bootRun
```

4. Accede a Eureka en:  
   `http://localhost:8761`