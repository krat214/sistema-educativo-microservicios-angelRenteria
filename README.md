# 📚 Sistema Educativo - Microservicios

Proyecto desarrollado para el parcial 2 de Microservicios.  
Contiene los servicios de usuarios, asignaturas y matrículas, comunicados entre sí mediante Eureka, Config Server y Feign Client.

## 👤 Datos del Estudiante

- **Nombre:** Angel Renteria
- **Asignatura:** LENGUAJE DE PROGRAMACIÓN AVANZADO 2
- **Profesor:** Martha Nicolasa Amaya Becerra

## 🏗️ Estructura del Repositorio

```
sistema-educativo-microservicios-angelRenteria/
├── config-server/                # Servidor de configuración centralizada
├── eureka-server/                # Servidor de descubrimiento de servicios
├── usuarios-servicio/            # Gestión de usuarios y autenticación
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/...          # Código fuente
│   │   │   └── resources/        # Configuraciones
│   │   └── test/                 # Pruebas unitarias
│   ├── build.gradle              # Dependencias
│   └── Dockerfile                # Configuración para Docker
├── asignaturas-servicio/         # Gestión de asignaturas
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/...          # Código fuente
│   │   │   └── resources/        # Configuraciones
│   │   └── test/                 # Pruebas unitarias
│   ├── build.gradle              # Dependencias
│   └── Dockerfile                # Configuración para Docker
├── matriculas-servicio/          # Gestión de matrículas
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/...          # Código fuente
│   │   │   └── resources/        # Configuraciones
│   │   └── test/                 # Pruebas unitarias
│   ├── build.gradle              # Dependencias
│   └── Dockerfile                # Configuración para Docker
├── docker-compose.yml            # Orquestación de contenedores
└── README.md                     # Este archivo
```

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

- Todos los servicios están configurados para cargar sus propiedades desde un **repositorio remoto en GitHub https://github.com/krat214/config-repo**.
- Las propiedades están organizadas por nombre del microservicio:  
  Ejemplo:  
  `usuarios-servicio.properties`, `asignaturas-servicio.properties`, `matriculas-servicio.properties`.

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
- Otros servicios pueden validar tokens para proteger rutas privadas.
- Los tokens son enviados mediante Feign Client en cada solicitud para mantener la autenticación entre microservicios.

---

## 🔍 Monitoreo

- Todos los servicios exponen endpoints vía **Spring Boot Actuator**.
- Se puede acceder a información de salud, beans, rutas, etc., desde `/actuator`.

---

## 🧪 Base de Datos

- Cada microservicio trabaja con **H2 en memoria** para pruebas locales.
- No se persiste información entre reinicios.

---

## 🏛️ Arquitectura del Sistema

```
┌────────────────┐      ┌────────────────┐
│                │      │                │
│  Config Server │◄─────┤  GitHub Repo   │
│    (8888)      │      │                │
│                │      └────────────────┘
└───────┬────────┘
        │
        │
┌───────▼────────┐
│                │
│  Eureka Server │
│    (8761)      │
│                │
└───────┬────────┘
        │
        ├─────────────┬─────────────┐
        │             │             │
┌───────▼──────┐ ┌────▼─────────┐ ┌─▼─────────────┐
│              │ │              │ │               │
│  Usuarios    │ │ Asignaturas  │ │  Matrículas   │
│  Servicio    │ │ Servicio     │ │  Servicio     │
│   (8081)     │ │  (8082)      │ │   (8083)      │
│              │ │              │ │               │
└──────────────┘ └──────────────┘ └───────────────┘
```

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

### 🐳 Uso con Docker

1. Construir las imágenes:
```bash
docker-compose build
```

2. Levantar todos los servicios:
```bash
docker-compose up
```

---