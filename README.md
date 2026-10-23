# AutoRent — API REST para alquiler de vehículos

AutoRent es una API backend para administrar vehículos, clientes y alquileres. Está desarrollada con Java y Spring Boot, utiliza PostgreSQL para persistencia y organiza el código siguiendo el enfoque de **Arquitectura Hexagonal (Ports and Adapters)**.

> Proyecto académico. La documentación describe las rutas y clases encontradas en la rama `master` del repositorio.

## Funcionalidades

- Registrar y consultar vehículos, incluidos los que están disponibles.
- Registrar y consultar clientes.
- Crear alquileres validando la existencia del cliente y del vehículo, la cantidad de días y la disponibilidad del vehículo.
- Calcular el valor del alquiler a partir del precio diario y la cantidad de días.
- Consultar alquileres y su información detallada.
- Finalizar alquileres y liberar el vehículo asociado.
- Devolver códigos HTTP para resultados exitosos y errores de negocio.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje del proyecto (`pom.xml`) |
| Spring Boot 4.1.1 | Arranque y configuración de la aplicación (`pom.xml`) |
| Spring Web MVC | Endpoints REST |
| Spring Data JPA / Hibernate | Persistencia relacional |
| PostgreSQL | Base de datos |
| Lombok | Reducción de código repetitivo |
| Maven Wrapper | Compilación y ejecución sin instalar Maven globalmente |

## Arquitectura

El diseño separa los adaptadores HTTP y de persistencia de las reglas de negocio. Los controladores invocan puertos de entrada; los servicios implementan esos puertos y usan el dominio junto con puertos de salida. Los adaptadores de persistencia implementan los puertos de salida usando Spring Data JPA.

![Diagrama de arquitectura de AutoRent](docs/arquitectura.svg)

- [Diagrama y explicación de la arquitectura](docs/arquitectura.md)
- [Fuente Mermaid editable](docs/arquitectura.mmd)
- [Diagrama SVG](docs/arquitectura.svg)
- [Diagrama PNG](docs/arquitectura.png)

### Estructura principal

```text
src/main/java/com/autorent/
├── AutorentApplication.java
├── domain/
│   ├── model/                 # Auto, Cliente, Alquiler, estados y modelo detallado
│   └── exception/             # Excepciones de negocio
├── application/
│   ├── port/
│   │   ├── input/              # Contratos de los casos de uso
│   │   └── output/             # Contratos de persistencia/consulta
│   ├── service/                # AutoService, ClienteService, AlquilerService
│   └── dto/
│       ├── request/            # DTOs de entrada
│       ├── response/           # DTOs de salida
│       └── mapper/              # Conversión entre DTOs y modelos
└── infrastructure/
    └── adapter/
        ├── input/rest/          # Controladores REST
        └── output/persistence/
            ├── adapter/         # Implementaciones de los puertos de salida
            ├── entity/          # Entidades JPA
            └── repository/      # Repositorios Spring Data JPA
```

### Responsabilidad por capa

| Capa | Responsabilidad |
|---|---|
| `domain` | Modelos, estados y excepciones de negocio; no representa tablas JPA. |
| `application.port.input` | Contratos de las acciones ofrecidas por la aplicación. |
| `application.service` | Implementa los casos de uso, valida reglas y coordina operaciones. |
| `application.port.output` | Declara las operaciones de consulta y persistencia requeridas por la aplicación. |
| `infrastructure.adapter.input.rest` | Recibe las solicitudes HTTP y prepara las respuestas. |
| `infrastructure.adapter.output.persistence` | Convierte entre modelos de dominio y entidades JPA y accede a PostgreSQL. |

## Reglas de negocio principales

- Una placa no puede registrarse dos veces.
- Los campos obligatorios de vehículo y cliente deben estar informados; los importes del vehículo deben ser mayores que cero.
- El documento y el correo electrónico del cliente deben ser únicos; el correo debe tener un formato válido.
- Para crear un alquiler deben existir el cliente y el vehículo, el vehículo debe estar `DISPONIBLE` y `cantidadDias` debe ser mayor que cero.
- El sistema calcula `valorTotal = precioPorDia × cantidadDias`; el cliente de la API no envía el valor total.
- Al crear un alquiler, el vehículo pasa a `ALQUILADO` y el alquiler queda `ACTIVO`.
- Al finalizar un alquiler, el alquiler pasa a `FINALIZADO` y el vehículo vuelve a `DISPONIBLE`.
- No se permite finalizar un alquiler que ya está finalizado.

## Endpoints

La URL base local es `http://localhost:8080`. Las rutas de clientes y alquileres están actualmente en **singular** (`/api/cliente` y `/api/alquiler`).

### Vehículos

| Método | Ruta | Descripción | Respuestas principales |
|---|---|---|---|
| `POST` | `/api/autos` | Registrar vehículo | `201`, `400`, `409` |
| `GET` | `/api/autos` | Listar vehículos | `200` |
| `GET` | `/api/autos/{id}` | Consultar vehículo por ID | `200`, `404` |
| `GET` | `/api/autos/disponibles` | Listar vehículos disponibles | `200` |

Cuerpo para `POST /api/autos`:

```json
{
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2025,
  "deposito": 300000,
  "precioPorDia": 150000
}
```

`deposito` y `precioPorDia` son campos diferentes. El campo `estado` no se necesita en la solicitud de registro: el servicio asigna `DISPONIBLE`.

### Clientes

| Método | Ruta | Descripción | Respuestas principales |
|---|---|---|---|
| `POST` | `/api/cliente` | Registrar cliente | `201`, `400`, `409` |
| `GET` | `/api/cliente` | Listar clientes | `200` |
| `GET` | `/api/cliente/{id}` | Consultar cliente por ID | `200`, `404` |

Cuerpo para `POST /api/cliente`:

```json
{
  "nombre": "Carlos Pérez",
  "documento": "123456789",
  "email": "carlos@example.com",
  "telefono": "3001234567"
}
```

### Alquileres

| Método | Ruta | Descripción | Respuestas principales |
|---|---|---|---|
| `POST` | `/api/alquiler` | Crear alquiler | `201`, `400`, `404`, `409` |
| `GET` | `/api/alquiler/{id}` | Consultar alquiler por ID | `200`, `404` |
| `GET` | `/api/alquiler/detallados` | Listar alquileres con información detallada | `200` |
| `GET` | `/api/alquiler/{id}/detallado` | Consultar detalle de un alquiler | `200`, `404` |
| `PUT` | `/api/alquiler/{id}/finalizar` | Finalizar alquiler | `200`, `404`, `409` |

Cuerpo para `POST /api/alquiler`:

```json
{
  "clienteId": 1,
  "autoId": 1,
  "cantidadDias": 5
}
```

No envíes `valorTotal`: se calcula en el backend a partir del precio diario guardado para el vehículo.

### Ejemplo rápido con cURL

```bash
curl -X POST http://localhost:8080/api/alquiler \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"autoId":1,"cantidadDias":5}'
```

Puedes reproducir los ejemplos desde Postman usando `http://localhost:8080` como base URL y `Content-Type: application/json` para solicitudes con cuerpo JSON.

### Códigos HTTP

| Código | Uso habitual |
|---|---|
| `200 OK` | Consulta o finalización completada |
| `201 Created` | Vehículo, cliente o alquiler creado |
| `400 Bad Request` | Datos faltantes o inválidos |
| `404 Not Found` | Vehículo, cliente o alquiler no encontrado |
| `409 Conflict` | Placa/documento/correo duplicado, vehículo no disponible o alquiler ya finalizado |

**Nota sobre errores:** el contrato de error todavía no está totalmente unificado en todos los endpoints. Algunas operaciones devuelven `ApiErrorResponse` (`status`, `error`, `message`) y otras devuelven un objeto JSON con la propiedad `Mensaje`. Para clientes que consuman la API, revisa el endpoint concreto antes de asumir una única estructura de error.

## Configuración y ejecución local

### Requisitos

- JDK 17.
- PostgreSQL accesible desde el equipo.
- Git (para clonar el proyecto).
- No es obligatorio instalar Maven globalmente; el repositorio incluye `mvnw` y `mvnw.cmd`.

### 1. Clonar el repositorio

```bash
git clone https://github.com/KevinOsorioDEV/autorent.git
cd autorent
```

### 2. Crear la base de datos

En PostgreSQL, crea la base si todavía no existe:

```sql
CREATE DATABASE autorent;
```

La configuración versionada tiene `spring.jpa.hibernate.ddl-auto=none`: **Hibernate no crea ni actualiza las tablas automáticamente**. Antes de llamar a los endpoints, aplica el esquema SQL requerido por las entidades y registra los estados que se referencian desde los vehículos y los alquileres (`estado_id`).

### 3. Configurar la conexión

Se pueden usar variables de entorno para sobrescribir los valores del archivo `src/main/resources/application.properties`.

**Windows PowerShell:**

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/autorent"
$env:SPRING_DATASOURCE_USERNAME = "postgres"
$env:SPRING_DATASOURCE_PASSWORD = "TU_CLAVE_LOCAL"
```

**Linux/macOS:**

```bash
export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/autorent"
export SPRING_DATASOURCE_USERNAME="postgres"
export SPRING_DATASOURCE_PASSWORD="TU_CLAVE_LOCAL"
```

Sustituye usuario y contraseña por los de tu instalación. No uses credenciales reales dentro de archivos versionados en Git.

### 4. Compilar y ejecutar

**Windows:**

```powershell
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw clean verify
./mvnw spring-boot:run
```

Por defecto, la API se publica en `http://localhost:8080`.

Para generar el JAR ejecutable:

```bash
./mvnw clean package
```

En Windows usa `.\mvnw.cmd clean package` desde la carpeta del proyecto.

## Pruebas

Ejecuta `./mvnw test` (Windows PowerShell: `./mvnw.cmd test`) para lanzar las pruebas automatizadas definidas en el proyecto. La compilación y las pruebas pueden requerir ajustes de configuración dependiendo de la base de datos que utilice cada prueba.

## Seguridad y configuración

- No publiques credenciales reales de PostgreSQL en `application.properties`.
- Si una contraseña versionada alguna vez fue una credencial real, cámbiala y evalúa retirarla también del historial de Git; editar el archivo actual no elimina versiones anteriores.
- `ddl-auto=none` mantiene el esquema bajo control explícito: no lo cambies a `create` o `update` sin revisar primero el impacto en tus datos.

## Estado del proyecto

La documentación describe el código observado en la rama `master`. Los ejemplos de payload corresponden a los DTOs actuales; si cambian rutas, DTOs o reglas de negocio, actualiza también esta documentación y el diagrama.
