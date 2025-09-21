-- Script para crear la base de datos y la tabla usuarios
CREATE DATABASE IF NOT EXISTS plataforma;
USE plataforma;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    clave VARCHAR(255),
    correo VARCHAR(100),
    telefono VARCHAR(20),
    roll VARCHAR(20) DEFAULT 'usuario',
    estado VARCHAR(20) DEFAULT 'Inactivo',
    codigoVerificacion VARCHAR(100),
    modoAutenticacion VARCHAR(20) DEFAULT 'Autoregistro',
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    direccionIP VARCHAR(50)
);
