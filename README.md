# 👨‍⚕️ Keepers API - Microservicio de Cuidadores

Microservicio independiente para la gestión de cuidadores de animales en el sistema del zoológico. Opera de forma completamente autónoma con su propia base de datos.

## 🚀 Características

- **Gestión de Cuidadores:** CRUD completo
- **Consultas Personalizadas:** Búsqueda por especialización, experiencia, nombre
- **Filtros Avanzados:** Por estado activo/inactivo
- **Base de datos:** MySQL independiente
- **Puerto:** 8081
- **Comunicación:** REST API

## 📋 Requisitos

- Java 21+
- Maven 3.6+
- MySQL 8.0+
- Spring Boot 3.1.0

## 🛠️ Configuración

### 1. Crear base de datos:

```sql
CREATE DATABASE keepers_db;
```

### 2. Configurar credenciales:

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/keepers_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

## ▶️ Ejecución

### Opción 1: Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Opción 2: JAR

```bash
mvn clean package
java -jar target/api-keepers-1.0.0.jar
```

El servicio estará disponible en: `http://localhost:8081`

## 📚 Endpoints

### CRUD Básico

#### Obtener todos los cuidadores

```http
GET /keepers
GET /keepers?is_active=true
```

#### Obtener cuidador por ID

```http
GET /keepers/{id}
```

#### Crear cuidador

```http
POST /keepers
Content-Type: application/json

{
  "id": 1,
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@zoo.com",
  "hireDate": "2020-05-15",
  "specialization": "Mamíferos",
  "isActive": true,
  "yearsOfExperience": 5
}
```

#### Actualizar cuidador

```http
PUT /keepers/{id}
Content-Type: application/json

{
  "id": 1,
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@zoo.com",
  "hireDate": "2020-05-15",
  "specialization": "Mamíferos y Grandes Felinos",
  "isActive": true,
  "yearsOfExperience": 6
}
```

#### Eliminar cuidador

```http
DELETE /keepers/{id}
```

### Búsquedas Especializadas

#### Por especialización

```http
GET /keepers/search/by-specialization?specialization=Mamíferos
```

#### Por experiencia mínima

```http
GET /keepers/search/by-experience?min_years=5
```

#### Por nombre (parcial)

```http
GET /keepers/search/by-name?name=carlos
```

#### Activos con especialización

```http
GET /keepers/search/active-by-specialization?specialization=Aves
```

### Health Check

```http
GET /health
```

Respuesta:

```json
{
  "status": "UP",
  "service": "Keepers API",
  "version": "1.0.0"
}
```

## 📝 Modelo de Datos

### Keeper

```json
{
  "id": 1,
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@zoo.com",
  "hireDate": "2020-05-15",
  "specialization": "Mamíferos",
  "isActive": true,
  "yearsOfExperience": 5
}
```

### Campos:

| Campo             | Tipo      | Requerido | Validación          |
| ----------------- | --------- | --------- | ------------------- |
| id                | Long      | Sí        | Único               |
| firstName         | String    | Sí        | Máx 100 caracteres  |
| lastName          | String    | Sí        | Máx 100 caracteres  |
| email             | String    | Sí        | Email válido, único |
| hireDate          | LocalDate | Sí        | Formato: yyyy-MM-dd |
| specialization    | String    | Sí        | Máx 100 caracteres  |
| isActive          | Boolean   | Sí        | -                   |
| yearsOfExperience | Integer   | Sí        | >= 0                |

## 🏗️ Estructura del Proyecto

```
src/main/java/com/example/keepers/
├── config/          # Configuraciones (CORS, etc)
├── controller/      # Controladores REST
├── exception/       # Manejo de excepciones
├── model/           # Entidades JPA
├── repository/      # Repositorios JPA con consultas personalizadas
└── service/         # Lógica de negocio
```

## 🧪 Ejemplos de Uso

### Crear un keeper con cURL:

```bash
curl -X POST http://localhost:8081/keepers \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@zoo.com",
  "hireDate": "2020-05-15",
  "specialization": "Mamíferos",
  "isActive": true,
  "yearsOfExperience": 5
}'
```

### Buscar keepers activos con especialización:

```bash
curl "http://localhost:8081/keepers/search/active-by-specialization?specialization=Mamíferos"
```

### Obtener keepers con experiencia mínima:

```bash
curl "http://localhost:8081/keepers/search/by-experience?min_years=5"
```

## 🔍 Consultas Personalizadas JPA

El repositorio incluye las siguientes consultas personalizadas:

- `findByEmail(String email)` - Buscar por email único
- `findByIsActive(Boolean isActive)` - Filtrar por estado
- `findBySpecialization(String specialization)` - Por especialización exacta
- `findByMinimumExperience(Integer minYears)` - Por experiencia mínima, ordenado DESC
- `findByNameContaining(String name)` - Búsqueda parcial en nombre/apellido
- `findByIsActiveAndSpecialization(Boolean, String)` - Combinación de filtros

## 🚨 Manejo de Errores

### Respuestas de Error:

#### 404 - Keeper no encontrado

```json
{
  "timestamp": "2025-11-17T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Keeper with id 999 not found"
}
```

#### 409 - Conflicto (ID o Email duplicado)

```json
{
  "timestamp": "2025-11-17T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Keeper with email carlos@zoo.com already exists"
}
```

#### 400 - Validación fallida

```json
{
  "timestamp": "2025-11-17T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "validationErrors": {
    "email": "Email must be valid",
    "firstName": "First name cannot be blank"
  }
}
```

## 🔐 CORS

CORS está habilitado para todos los orígenes (`*`). Modificar en `WebConfig.java` para producción.

## 🔗 Integración con Animals API

Este microservicio es consumido por **Animals API** para:

- Validar la existencia de keepers al asignarlos a animales
- Obtener información completa de keepers
- Proporcionar datos para vistas combinadas Animal-Keeper

Animals API se comunica con Keepers API mediante WebClient (REST).

## 📊 Datos de Prueba

Crear algunos keepers de ejemplo:

```bash
# Keeper 1
curl -X POST http://localhost:8081/keepers \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@zoo.com",
  "hireDate": "2020-05-15",
  "specialization": "Mamíferos",
  "isActive": true,
  "yearsOfExperience": 5
}'

# Keeper 2
curl -X POST http://localhost:8081/keepers \
-H "Content-Type: application/json" \
-d '{
  "id": 2,
  "firstName": "María",
  "lastName": "González",
  "email": "maria@zoo.com",
  "hireDate": "2019-03-20",
  "specialization": "Aves",
  "isActive": true,
  "yearsOfExperience": 6
}'

# Keeper 3
curl -X POST http://localhost:8081/keepers \
-H "Content-Type: application/json" \
-d '{
  "id": 3,
  "firstName": "Juan",
  "lastName": "Martínez",
  "email": "juan@zoo.com",
  "hireDate": "2021-08-10",
  "specialization": "Reptiles",
  "isActive": true,
  "yearsOfExperience": 3
}'
```

## 🐛 Troubleshooting

### Error de conexión a MySQL

- Verificar que MySQL esté corriendo
- Verificar credenciales en `application.properties`
- Verificar que la base de datos `keepers_db` exista

### Puerto 8081 en uso

- Cambiar puerto en `application.properties`: `server.port=OTRO_PUERTO`
- Actualizar la URL en Animals API si se cambia el puerto

### Conflicto de ID o Email

- Los IDs y emails deben ser únicos
- Verificar que no exista otro keeper con ese ID o email

## 🔄 Orden de Inicio

En un entorno de desarrollo, iniciar en este orden:

1. MySQL Server
2. **Keepers API** (puerto 8081) ← Este servicio
3. Animals API (puerto 8080)

## 📦 Versión

**1.0.0** - Noviembre 2025

## 👥 Autor

Desarrollo de Aplicaciones Empresariales - 2025B

## 📖 Documentación Adicional

Para información sobre la integración completa del sistema de microservicios, ver:

- `DOCUMENTACION_FRONTEND_MICROSERVICIOS.md` en el proyecto Animals API
