-- Datos de referencia: los nombres deben coincidir con los enums EstadoAuto y EstadoAlquiler.

INSERT INTO estado_auto (descripcion)
SELECT v.d
FROM (VALUES ('DISPONIBLE'), ('ALQUILADO'), ('MANTENIMIENTO')) AS v (d)
WHERE NOT EXISTS (SELECT 1 FROM estado_auto e WHERE e.descripcion = v.d);

INSERT INTO estado_alquiler (descripcion)
SELECT v.d
FROM (VALUES ('ACTIVO'), ('FINALIZADO')) AS v (d)
WHERE NOT EXISTS (SELECT 1 FROM estado_alquiler e WHERE e.descripcion = v.d);
