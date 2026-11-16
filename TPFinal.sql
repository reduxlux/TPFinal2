CREATE DATABASE IF NOT EXISTS Hotel;

-- DATOS DE PRUEBA - SISTEMA HOTEL
USE Hotel;

START TRANSACTION;

-- 1. ADMINISTRADORES
INSERT IGNORE INTO administradores
(id, nombre, usuario, contrasenia) VALUES
(101, 'Simon', 'simon', 'Admin123!'),
(102, 'Thaiel', 'thaiel', 'Admin123!'),
(103, 'Lucia', 'lucia', 'Admin123!');

-- 2. RECEPCIONISTAS
INSERT IGNORE INTO recepcionistas
(id, nombre, usuario, contrasenia) VALUES
(201, 'Martina Lopez', 'martina', 'Recep123!'),
(202, 'Federico Ruiz', 'federico', 'Recep123!'),
(203, 'Valentina Gomez', 'valentina', 'Recep123!');

-- 3. PASAJEROS
INSERT IGNORE INTO pasajeros
(dni, nombre, origen, domicilio_origen) VALUES
('40000101', 'Juan Perez', 'Mar del Plata', 'Colon 1250'),
('40000102', 'Camila Torres', 'Balcarce', 'Calle 18 640'),
('40000103', 'Mateo Gonzalez', 'Buenos Aires', 'Santa Fe 2100'),
('40000104', 'Sofia Martinez', 'La Plata', 'Diagonal 74 850'),
('40000105', 'Lucas Fernandez', 'Tandil', 'San Martin 520'),
('40000106', 'Valeria Romero', 'Necochea', 'Av. 59 1440'),
('40000107', 'Nicolas Diaz', 'Olavarria', 'Rivadavia 330'),
('40000108', 'Agustina Silva', 'Miramar', 'Calle 21 715');

-- 4. HABITACIONES
INSERT IGNORE INTO habitaciones
(numero, piso, tipo, estado, motivo_no_disponible) VALUES
(101, 1, 'SIMPLE', 'RESERVADA', ''),
(102, 1, 'DOBLE', 'RESERVADA', ''),
(103, 1, 'DOBLE', 'DISPONIBLE', ''),
(104, 1, 'TRIPLE', 'RESERVADA', ''),
(201, 2, 'SIMPLE', 'RESERVADA', ''),
(202, 2, 'DOBLE', 'DISPONIBLE', ''),
(203, 2, 'SUITE', 'RESERVADA', ''),
(204, 2, 'TRIPLE', 'DISPONIBLE', ''),
(301, 3, 'SUITE', 'RESERVADA', ''),
(302, 3, 'DOBLE', 'NO_DISPONIBLE',
 'Mantenimiento de aire acondicionado'),
(303, 3, 'SIMPLE', 'DISPONIBLE', ''),
(304, 3, 'TRIPLE', 'DISPONIBLE', '');

-- 5. RESERVAS
INSERT IGNORE INTO reservas
(id, dni_pasajero, numero_habitacion,
 fecha_entrada, fecha_salida, estado) VALUES
(501, '40000101', 101, '2026-11-17', '2026-11-20', 'CONFIRMADA'),
(502, '40000102', 102, '2026-11-18', '2026-11-22', 'CONFIRMADA'),
(503, '40000103', 104, '2026-11-19', '2026-11-23', 'CONFIRMADA'),
(504, '40000104', 201, '2026-11-20', '2026-11-25', 'CONFIRMADA'),
(505, '40000105', 203, '2026-11-21', '2026-11-24', 'CONFIRMADA'),
(506, '40000106', 301, '2026-11-18', '2026-11-20', 'CONFIRMADA'),
(507, '40000107', 202, '2026-11-22', '2026-11-25', 'CANCELADA');

-- 6. ESTADIAS FINALIZADAS
INSERT IGNORE INTO estadias
(dni_pasajero, numero_habitacion,
 fecha_check_in, fecha_check_out) VALUES
('40000101', 103, '2026-10-05', '2026-10-08'),
('40000102', 202, '2026-10-12', '2026-10-15'),
('40000103', 204, '2026-10-18', '2026-10-21'),
('40000104', 303, '2026-10-23', '2026-10-26'),
('40000105', 304, '2026-11-01', '2026-11-04'),
('40000108', 103, '2026-11-07', '2026-11-10');

COMMIT;

-- 7. COMPROBAR LOS DATOS
SELECT * FROM administradores ORDER BY id;
SELECT * FROM recepcionistas ORDER BY id;
SELECT * FROM pasajeros ORDER BY nombre;
SELECT * FROM habitaciones ORDER BY numero;
SELECT * FROM reservas ORDER BY fecha_entrada, id;
SELECT * FROM estadias ORDER BY fecha_check_in;