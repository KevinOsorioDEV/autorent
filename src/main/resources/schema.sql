-- AutoRent: esquema de base de datos (PostgreSQL)
-- Idempotente: se puede ejecutar en cada arranque sin duplicar nada.

CREATE TABLE IF NOT EXISTS estado_auto (
    id          SERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estado_alquiler (
    id          BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS auto (
    id         BIGSERIAL PRIMARY KEY,
    placa      VARCHAR(20)  NOT NULL UNIQUE,
    modelo     VARCHAR(100) NOT NULL,
    marca      VARCHAR(100) NOT NULL,
    anio       INTEGER      NOT NULL,
    deposito   DOUBLE PRECISION,
    precio_dia DOUBLE PRECISION NOT NULL,
    estado_id  INTEGER      NOT NULL REFERENCES estado_auto (id)
);

CREATE TABLE IF NOT EXISTS cliente (
    id        BIGSERIAL PRIMARY KEY,
    nombre    VARCHAR(250) NOT NULL,
    documento VARCHAR(50)  NOT NULL UNIQUE,
    email     VARCHAR(250) NOT NULL UNIQUE,
    telefono  VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS alquiler (
    id            BIGSERIAL PRIMARY KEY,
    cliente_id    BIGINT NOT NULL REFERENCES cliente (id),
    auto_id       BIGINT NOT NULL REFERENCES auto (id),
    fecha_inicio  DATE   NOT NULL,
    fecha_fin     DATE,
    cantidad_dias INTEGER NOT NULL,
    valor_total   DOUBLE PRECISION NOT NULL,
    estado_id     BIGINT NOT NULL REFERENCES estado_alquiler (id)
);

-- Vista usada por GET /alquileres/detallados y /{id}/detallado
DROP VIEW IF EXISTS vw_alquileres_detalle;
CREATE VIEW vw_alquileres_detalle AS
SELECT a.id,
       a.cliente_id,
       c.nombre,
       c.documento,
       a.auto_id,
       au.placa,
       a.fecha_inicio,
       a.fecha_fin,
       a.cantidad_dias,
       a.valor_total,
       e.descripcion AS estado
FROM alquiler a
         JOIN cliente c ON c.id = a.cliente_id
         JOIN auto au ON au.id = a.auto_id
         JOIN estado_alquiler e ON e.id = a.estado_id;

