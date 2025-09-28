-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS plataforma;
USE plataforma;

-- Creación de la tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    roll VARCHAR(20),
    estado VARCHAR(20),
    codigoVerificacion VARCHAR(100),
    modoAutenticacion VARCHAR(50),
    direccionIP VARCHAR(45),
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserción de datos de ejemplo
INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, roll, estado, codigoVerificacion, modoAutenticacion, direccionIP)
VALUES 
('Juan', 'Perez', 'clave123', 'juan.perez@example.com', '1234567890', 'admin', 'activo', '12345', 'email', '192.168.1.1'),
('Maria', 'Lopez', 'clave456', 'maria.lopez@example.com', '0987654321', 'usuario', 'activo', '67890', 'sms', '192.168.1.2');