# 🚗 AutoRent — Sistema de Alquiler de Autos

Microservicio para la gestión de alquiler de vehículos, desarrollado con **Java y Spring Boot** aplicando **Arquitectura Hexagonal (Ports and Adapters)** y **PostgreSQL** como mecanismo de persistencia.

> Proyecto de clase — Programación Web Backend (UPCA)
> Docente: Ing. Iván Devia Acosta

---

## 👥 Integrantes

| Nombre                    | Rol |
|---------------------------|-----|
| _Kevin Osorio_            | _Desarrollador_ |


---

## 🎯 Objetivo

Permitir a una empresa de alquiler de autos controlar:

- Qué vehículos tiene y cuáles están disponibles.
- Qué clientes están registrados.
- Cuándo inicia y finaliza un alquiler.
- Cuánto debe pagar un cliente.

El sistema está construido para que la tecnología externa (REST, PostgreSQL) pueda cambiar **sin modificar las reglas de negocio**.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| Java 17+ | Lenguaje principal |
| Spring Boot | Framework y exposición de servicios REST |
| Spring Web | Adaptador de entrada (Controllers) |
| Spring Data JPA | Adaptador de salida (persistencia) |
| PostgreSQL | Base de datos relacional |
| PostgreSQL JDBC Driver | Conexión a PostgreSQL |
| Bean Validation | Validación de datos de entrada |
| Maven | Gestión de dependencias |

---

## ▶️ Cómo ejecutar el proyecto

### Requisitos previos

- JDK 17 o superior
- Maven 3.8+ (o usar el wrapper `mvnw` incluido)
- PostgreSQL 14+ instalado y en ejecución (o vía Docker)

### Pasos

```bash
# 1. Clonar el repositorio
git clone <URL_DEL_REPOSITORIO>
cd autorent

# 2. Crear la base de datos en PostgreSQL
psql -U postgres -c "CREATE DATABASE autorent;"

# 3. Configurar credenciales en src/main/resources/application.properties

# 4. Compilar
./mvnw clean install

# 5. Ejecutar
./mvnw spring-boot:run
```

> Alternativa con Docker:
>
> ```bash
> docker run --name autorent-db -e POSTGRES_DB=autorent -e POSTGRES_USER=postgres \
>   -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16
> ```

La aplicación quedará disponible en: **http://localhost:8080**

### Verificar los datos en PostgreSQL

Puedes consultar las tablas con `psql`, pgAdmin, DBeaver o la herramienta que prefieras:

```bash
psql -U postgres -d autorent
```

```sql
SELECT * FROM autos;
SELECT * FROM clientes;
SELECT * FROM alquileres;
```

> Los nombres de las tablas dependen de cómo anotes tus entidades JPA (`@Table`).

### Configuración (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/autorent
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=NONE
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

Dependencia en `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 🏗️ Arquitectura

El proyecto implementa **Arquitectura Hexagonal**. El núcleo (dominio + aplicación) no conoce a Spring, JPA ni PostgreSQL; son los adaptadores quienes se conectan a él a través de puertos (interfaces).

### Diagrama

```
                    ┌──────────────────────────┐
                    │   Cliente HTTP / Postman │
                    └────────────┬─────────────┘
                                 │
 ════════════════════ INFRAESTRUCTURA ════════════════════
                                 ▼
                    ┌──────────────────────────┐
                    │        REST API          │
                    │   Adaptador de entrada   │
                    │ (Auto/Cliente/Alquiler   │
                    │       Controller)        │
                    └────────────┬─────────────┘
 ═══════════════════════ NÚCLEO ═════════════════════════
                                 ▼
                    ┌──────────────────────────┐
                    │   PUERTOS DE ENTRADA     │
                    │   (Use Cases)            │
                    └────────────┬─────────────┘
                                 ▼
                    ┌──────────────────────────┐
                    │  APLICACIÓN (Services)   │
                    │  Orquesta casos de uso   │
                    └────────────┬─────────────┘
                                 ▼
                    ┌──────────────────────────┐
                    │        DOMINIO           │
                    │  Auto · Cliente · Alquiler│
                    │  + reglas de negocio     │
                    └────────────┬─────────────┘
                                 ▼
                    ┌──────────────────────────┐
                    │   PUERTOS DE SALIDA      │
                    │   (Repository Ports)     │
                    └────────────┬─────────────┘
 ════════════════════ INFRAESTRUCTURA ════════════════════
                                 ▼
                    ┌──────────────────────────┐
                    │  Adaptadores de          │
                    │  persistencia            │
                    │  (PersistenceAdapter +   │
                    │   Spring Data JPA)       │
                    └────────────┬─────────────┘
                                 ▼
                          ┌────────────┐
                          │ PostgreSQL │
                          └────────────┘
```

### Estructura de paquetes

```
com.autorent
│
├── domain
│   ├── model
│   │   ├── Auto.java
│   │   ├── Cliente.java
│   │   └── Alquiler.java
│   └── exception
│       ├── AutoNoDisponibleException.java
│       ├── AutoNoEncontradoException.java
│       ├── ClienteNoEncontradoException.java
│       └── ...
│
├── application
│   ├── port
│   │   ├── input
│   │   │   ├── RegistrarAutoUseCase.java
│   │   │   ├── RegistrarClienteUseCase.java
│   │   │   ├── CrearAlquilerUseCase.java
│   │   │   ├── FinalizarAlquilerUseCase.java
│   │   │   └── ConsultarAutosDisponiblesUseCase.java
│   │   └── output
│   │       ├── AutoRepositoryPort.java
│   │       ├── ClienteRepositoryPort.java
│   │       └── AlquilerRepositoryPort.java
│   └── service
│       ├── AutoService.java
│       ├── ClienteService.java
│       └── AlquilerService.java
│
└── infrastructure
    ├── adapter
    │   ├── input
    │   │   └── rest
    │   │       ├── AutoController.java
    │   │       ├── ClienteController.java
    │   │       └── AlquilerController.java
    │   └── output
    │       └── persistence
    │           ├── entity       (AutoEntity, ClienteEntity, AlquilerEntity)
    │           ├── repository   (AutoJpaRepository, ClienteJpaRepository, AlquilerJpaRepository)
    │           └── adapter      (AutoPersistenceAdapter, ClientePersistenceAdapter, AlquilerPersistenceAdapter)
    └── config
        └── BeanConfiguration.java
```

### Responsabilidad de cada capa

| Capa | Responsabilidad | Depende de |
|------|-----------------|------------|
| **Dominio** | Entidades y reglas de negocio | Nada (Java puro) |
| **Aplicación** | Casos de uso y servicios que coordinan el flujo | Dominio |
| **Puertos de entrada** | Operaciones que la aplicación ofrece | Dominio |
| **Puertos de salida** | Operaciones que la aplicación necesita del exterior | Dominio |
| **Adaptadores de entrada** | Reciben HTTP y llaman a los casos de uso | Puertos de entrada |
| **Adaptadores de salida** | Implementan los puertos usando Spring Data JPA + PostgreSQL | Puertos de salida |

### Flujo de `POST /api/alquileres`

```
Cliente HTTP
   ↓
AlquilerController            (adaptador de entrada)
   ↓
CrearAlquilerUseCase          (puerto de entrada)
   ↓
AlquilerService               (aplicación)
   ├─► ClienteRepositoryPort  → ClientePersistenceAdapter → ClienteJpaRepository → PostgreSQL   (verifica que el cliente exista)
   ├─► AutoRepositoryPort     → AutoPersistenceAdapter    → AutoJpaRepository    → PostgreSQL   (busca el auto y valida disponibilidad)
   ├─► Dominio: calcula valorTotal y cambia el auto a ALQUILADO
   ├─► AutoRepositoryPort     → guarda el auto actualizado
   └─► AlquilerRepositoryPort → AlquilerPersistenceAdapter → AlquilerJpaRepository → PostgreSQL (guarda el alquiler)
```

**Qué ocurre con cada entidad:**

- **Cliente:** solo se consulta para verificar que existe.
- **Auto:** se consulta, se valida que esté `DISPONIBLE` y pasa a `ALQUILADO`.
- **Alquiler:** se crea con estado `ACTIVO` y con el `valorTotal` calculado por el sistema.

### Separación dominio / persistencia

Las clases del dominio (`Auto`, `Cliente`, `Alquiler`) **no** tienen anotaciones de JPA ni de Spring. Las entidades de persistencia (`AutoEntity`, `ClienteEntity`, `AlquilerEntity`) viven en la infraestructura, y los adaptadores de persistencia son los responsables de convertir entre ambas.

---

## 📦 Modelo de datos

### Auto

| Campo | Descripción |
|-------|-------------|
| `id` | Identificador |
| `placa` | Placa del vehículo |
| `marca` | Marca |
| `modelo` | Modelo |
| `anio` | Año |
| `precioPorDia` | Valor del alquiler por día |
| `estado` | `DISPONIBLE`, `ALQUILADO`, `MANTENIMIENTO` |

### Cliente

| Campo | Descripción |
|-------|-------------|
| `id` | Identificador |
| `nombre` | Nombre completo |
| `documento` | Documento de identificación (único) |
| `telefono` | Teléfono |
| `email` | Correo electrónico |

### Alquiler

| Campo | Descripción |
|-------|-------------|
| `id` | Identificador |
| `clienteId` | Cliente que realiza el alquiler |
| `autoId` | Auto alquilado |
| `fechaInicio` | Fecha de inicio |
| `fechaFin` | Fecha de finalización |
| `cantidadDias` | Cantidad de días |
| `valorTotal` | Valor calculado por el sistema |
| `estado` | `ACTIVO`, `FINALIZADO` |

---

## 📜 Reglas de negocio

1. **Solo se pueden alquilar autos disponibles.** Un auto `ALQUILADO` o en `MANTENIMIENTO` no puede alquilarse.
2. **Al alquilar, el auto pasa de `DISPONIBLE` a `ALQUILADO`.**
3. **Al finalizar un alquiler, el auto vuelve a `DISPONIBLE`.**
4. **No se puede finalizar dos veces el mismo alquiler.**
5. **La cantidad de días debe ser mayor que cero** (no se aceptan `0`, `-1`, `-5`, etc.).
6. **El valor total se calcula automáticamente:** `valorTotal = precioPorDia × cantidadDias`. El cliente nunca envía este valor.

### Validaciones

| Entidad | Validaciones |
|---------|--------------|
| **Auto** | Placa, marca y modelo obligatorios; precio por día mayor que cero |
| **Cliente** | Nombre y documento obligatorios; email válido; documento no repetido |
| **Alquiler** | Cliente existente; auto existente; auto disponible; días > 0; no finalizar dos veces |

---

## 🌐 Endpoints

### Autos

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/autos` | Registrar un auto |
| `GET` | `/api/autos/{id}` | Consultar auto por ID |
| `GET` | `/api/autos/disponibles` | Listar autos disponibles |

### Clientes

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/clientes` | Registrar un cliente |
| `GET` | `/api/clientes/{id}` | Consultar cliente por ID |

### Alquileres

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/alquileres` | Crear un alquiler |
| `GET` | `/api/alquileres/{id}` | Consultar un alquiler |
| `PUT` | `/api/alquileres/{id}/finalizar` | Finalizar un alquiler |

---

## 🧪 Ejemplos de solicitudes y respuestas

> Los cuerpos de respuesta son ilustrativos; ajústalos a los DTOs reales de tu implementación.

### 1. Crear un auto

**Solicitud** — `POST /api/autos`

```json
{
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2025,
  "precioPorDia": 150000
}
```

**Respuesta** — `201 Created`

```json
{
  "id": 1,
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2025,
  "precioPorDia": 150000,
  "estado": "DISPONIBLE"
}
```

### 2. Consultar un auto

**Solicitud** — `GET /api/autos/1`

**Respuesta** — `200 OK`

```json
{
  "id": 1,
  "placa": "ABC123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "anio": 2025,
  "precioPorDia": 150000,
  "estado": "DISPONIBLE"
}
```

### 3. Crear un cliente

**Solicitud** — `POST /api/clientes`

```json
{
  "nombre": "Carlos Pérez",
  "documento": "123456789",
  "telefono": "3001234567",
  "email": "carlos@email.com"
}
```

**Respuesta** — `201 Created`

```json
{
  "id": 1,
  "nombre": "Carlos Pérez",
  "documento": "123456789",
  "telefono": "3001234567",
  "email": "carlos@email.com"
}
```

### 4. Crear un alquiler

**Solicitud** — `POST /api/alquileres`

```json
{
  "clienteId": 1,
  "autoId": 1,
  "cantidadDias": 5
}
```

**Respuesta** — `201 Created`

```json
{
  "id": 1,
  "clienteId": 1,
  "autoId": 1,
  "fechaInicio": "2026-10-08",
  "fechaFin": "2026-10-13",
  "cantidadDias": 5,
  "valorTotal": 750000,
  "estado": "ACTIVO"
}
```

### 5. Finalizar un alquiler

**Solicitud** — `PUT /api/alquileres/1/finalizar`

**Respuesta** — `200 OK`

```json
{
  "id": 1,
  "clienteId": 1,
  "autoId": 1,
  "cantidadDias": 5,
  "valorTotal": 750000,
  "estado": "FINALIZADO"
}
```

### Ejemplos de errores

**Auto no disponible** — `409 Conflict`

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "El auto con id 1 no está disponible para alquiler"
}
```

**Auto o cliente no encontrado** — `404 Not Found`

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Auto no encontrado con id 99"
}
```

**Datos inválidos** — `400 Bad Request`

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "La cantidad de días debe ser mayor que cero"
}
```

**Finalizar un alquiler ya finalizado** — `409 Conflict`

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "El alquiler 1 ya fue finalizado"
}
```

### Resumen de códigos HTTP

| Situación | Código |
|-----------|--------|
| Recurso creado | `201 Created` |
| Consulta / finalización exitosa | `200 OK` |
| Datos inválidos | `400 Bad Request` |
| Auto / cliente / alquiler no encontrado | `404 Not Found` |
| Auto no disponible / alquiler ya finalizado / documento duplicado | `409 Conflict` |

---

## ✅ Pruebas realizadas

| # | Prueba | Resultado esperado |
|---|--------|--------------------|
| 1 | Crear un auto | `201`, estado `DISPONIBLE` |
| 2 | Consultar el auto | `200` |
| 3 | Crear un cliente | `201` |
| 4 | Crear un alquiler | `201`, `valorTotal` calculado |
| 5 | Verificar que el auto pasó a `ALQUILADO` | `GET /api/autos/{id}` → `ALQUILADO` |
| 6 | Alquilar nuevamente el mismo auto | `409 Conflict` |
| 7 | Finalizar el alquiler | `200`, estado `FINALIZADO` |
| 8 | Verificar que el auto regresó a `DISPONIBLE` | `GET /api/autos/{id}` → `DISPONIBLE` |
| 9 | Finalizar nuevamente el alquiler | `409 Conflict` |

📸 Las capturas de evidencia (endpoints, consulta de datos en PostgreSQL, caso exitoso y caso de error) se encuentran en la carpeta `/evidencias`.

---

## ❓ Preguntas clave de la arquitectura

**¿Podría cambiar la tecnología externa sin modificar las reglas de negocio?** Sí.

- **PostgreSQL → otro motor (MySQL, Oracle, etc.):** solo cambia la dependencia del driver y la configuración de `application.properties`. Dominio, casos de uso y puertos permanecen intactos.
- **REST → otro mecanismo de entrada** (gRPC, consola, mensajería): se crea un nuevo adaptador de entrada que use los mismos casos de uso.

**¿Quién depende de quién?** Las dependencias apuntan siempre hacia el núcleo:

```
Adaptadores (infraestructura) ──► Puertos ──► Aplicación ──► Dominio
```

El dominio no depende de nadie.

---

## 🔄 Extensibilidad (ejemplo: cancelar un alquiler)

Si se necesitara cancelar un alquiler antes de su fecha de inicio, el cambio se haría así:

1. **Regla de negocio nueva:** solo se puede cancelar un alquiler que aún no ha iniciado.
2. **Dominio:** nuevo estado `CANCELADO` y método de cancelación en `Alquiler`.
3. **Puerto de entrada:** `CancelarAlquilerUseCase`.
4. **Aplicación:** implementación en `AlquilerService`.
5. **Adaptador REST:** nuevo endpoint (por ejemplo, `PUT /api/alquileres/{id}/cancelar`).
6. **Persistencia:** ajuste mínimo en la entidad y el mapeo para el nuevo estado.

---

## 📄 Licencia

Proyecto con fines académicos.
