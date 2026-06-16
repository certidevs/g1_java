# 🎬 Gestión de Cine

> Aplicación web para **gestionar películas, salas, funciones, reservas y venta de entradas** — construida con Spring Boot, renderizado en servidor con Thymeleaf y Bootstrap.

<p align="center">
  <img src="https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.5-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 4">
  <img src="https://img.shields.io/badge/Thymeleaf-server--side-005F0F?logo=thymeleaf&logoColor=white" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/Bootstrap-5.3-7952B3?logo=bootstrap&logoColor=white" alt="Bootstrap 5">
  <img src="https://img.shields.io/badge/DB-H2%20%2F%20PostgreSQL-1F6FEB" alt="H2 / PostgreSQL">
  <img src="https://img.shields.io/badge/Security-Spring%20Security-6DB33F?logo=springsecurity&logoColor=white" alt="Spring Security">
</p>

---

## ¿Qué es?

**Gestión de Cine** es una aplicación web completa (no una API JSON, sino HTML renderizado en servidor) donde:

- **Cualquier visitante** puede consultar la cartelera, explorar películas y ver horarios disponibles.
- **Usuarios registrados** pueden reservar asientos, comprar entradas y consultar su historial.
- **Administradores** gestionan películas, salas, funciones, usuarios y ventas desde el panel de administración.

Todo con autenticación real, control de acceso por roles, subida de imágenes y datos de demostración listos para usar.

---

## Funcionalidades principales

### 🎥 Cartelera y películas

- Listado de películas con filtros por género, duración, clasificación y título.
- Ficha completa de cada película con sinopsis, duración, director, reparto y tráiler.
- Consulta de funciones disponibles por fecha y sala.
- Solo se muestran películas activas en cartelera.

### 🏢 Gestión de salas

- Administración de salas de cine.
- Configuración de capacidad y distribución de asientos.
- Gestión de salas estándar, 3D y VIP.
- Control de disponibilidad en tiempo real.

### 🎟️ Reservas y venta de entradas

- Reserva de asientos desde el mapa de la sala.
- Compra de entradas con validación de datos de pago.
- Generación automática de tickets.
- Cancelación de reservas según las políticas definidas.

### 👥 Cuentas y perfil

- Registro e inicio de sesión con Spring Security.
- Historial completo de compras y reservas.
- Perfil de usuario con estadísticas de uso.
- Gestión de datos personales y avatar.

### ⚙️ Panel de administración

- CRUD de películas.
- CRUD de salas.
- Gestión de funciones y horarios.
- Gestión de usuarios y permisos.
- Consulta de estadísticas de ventas y ocupación.

---

## Modelo de datos

| Entidad | Descripción |
|----------|-------------|
| **Movie** | Película: título, sinopsis, duración, género, clasificación, fecha de estreno e imagen. |
| **Room** | Sala de cine con capacidad, tipo y distribución de asientos. |
| **Screening** | Función asociada a una película, sala, fecha y hora. |
| **Seat** | Asiento disponible dentro de una sala. |
| **Reservation** | Reserva realizada por un usuario para una función. |
| **Ticket** | Entrada generada para una reserva confirmada. |
| **User** | Usuario con rol (`ROLE_USER` / `ROLE_ADMIN`), implementa `UserDetails`. |
| **Payment** | Información del pago asociado a una compra. |

---

## Stack tecnológico

- **Java 25** + **Spring Boot 4.0.5**
- **Spring MVC** + **Thymeleaf** (renderizado en servidor)
- **Spring Data JPA** sobre **H2** en memoria o **PostgreSQL 18**
- **Spring Security** (login por formulario, roles y BCrypt)
- **Spring Validation** (Jakarta Bean Validation)
- **Bootstrap 5.3** + **Font Awesome 7**
- **Lombok**
- **GitHub Actions** para integración continua

### Detalles de diseño destacados

- **Borrado lógico** de películas y salas para conservar el histórico.
- **Control de disponibilidad de asientos** evitando reservas duplicadas.
- **Páginas de error personalizadas** (403, 404 y 500).
- **Subida de imágenes** para carteles de películas.
- **Carga automática de datos de demostración** mediante `DataInitializer`.

---

## Cómo arrancarlo en local

> Requisitos: **JDK 25**. El proyecto incluye el wrapper de Maven.

```bash
# Clonar repositorio
git clone https://github.com/<tu-usuario>/gestion-cine.git

cd gestion-cine
```

### Bases de datos y perfiles

| Perfil | Base de datos | ¿Docker? | Uso |
|----------|--------------|:--------:|------|
| `dev` | H2 en memoria | No | Desarrollo |
| `prod` | PostgreSQL 18 | Sí | Producción |
| `test` | H2 en memoria | No | Tests |

---

### Opción A: H2 (por defecto)

```bash
./mvnw spring-boot:run
```

Abrir:

```text
http://localhost:8080
```

Consola H2:

```text
http://localhost:8080/h2-console
```

---

### Opción B: PostgreSQL con Docker

```bash
docker compose up -d
```

Ejecutar la aplicación:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Detener PostgreSQL:

```bash
docker compose down
```

---

### Cuentas de demostración

| Usuario | Contraseña | Rol |
|----------|-----------|------|
| `admin` | `admin` | Administrador |
| `user` | `user` | Usuario estándar |

---

## Permisos por rol

| Acción | Visitante | Usuario | Admin |
|----------|:--------:|:-------:|:-----:|
| Ver cartelera | Sí | Sí | Sí |
| Consultar funciones | Sí | Sí | Sí |
| Reservar entradas | No | Sí | Sí |
| Comprar entradas | No | Sí | Sí |
| Ver historial de compras | No | Sí | Sí |
| Gestionar películas | No | No | Sí |
| Gestionar salas | No | No | Sí |
| Gestionar usuarios | No | No | Sí |

---

## Estructura del proyecto

```text
src/main/java/com/cine
├── config/        # Seguridad y carga de datos demo
├── controller/    # Controladores MVC
├── dto/           # Formularios y objetos de transferencia
├── model/         # Entidades JPA
├── repository/    # Repositorios Spring Data JPA
└── service/       # Lógica de negocio

src/main/resources
├── templates/     # Vistas Thymeleaf
├── static/        # CSS, JS e imágenes
├── application.properties
├── application-dev.properties
└── application-prod.properties
```

---

## Estadísticas disponibles

- Películas más vistas.
- Salas con mayor ocupación.
- Ingresos por período.
- Entradas vendidas por película.
- Usuarios con más reservas.

---

## Posibles mejoras futuras

- Integración con pasarelas de pago reales.
- Generación de entradas en PDF con QR.
- Sistema de promociones y descuentos.
- Notificaciones por correo electrónico.
- API REST para aplicaciones móviles.
- Sistema de valoración y comentarios de películas.

---

## Capturas

![Cartelera](docs/screenshots/cartelera.png)

![Reserva de asientos](docs/screenshots/reserva.png)

---

<p align="center">
  Hecho con Java y Spring Boot 🎬
</p>