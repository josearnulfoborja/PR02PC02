-- Crear base de datos
CREATE DATABASE IF NOT EXISTS plataforma 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE plataforma;

-- Crear tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    roll VARCHAR(20) DEFAULT 'usuario',
    estado VARCHAR(20) DEFAULT 'Inactivo',
    codigoVerificacion VARCHAR(100),
    modoAutenticacion VARCHAR(20) DEFAULT 'Autoregistro',
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    direccionIP VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;