-- ==============================================================
-- SCRIPT DE BASE DE DATOS PARA SISTEMA DE RESERVAS DE HOTEL
-- Base de datos: plataforma
-- Tabla: usuarios
-- Desarrollado por: Borja y equipo
-- ==============================================================

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS plataforma
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE plataforma;

-- Eliminar tabla si existe (para desarrollo)
DROP TABLE IF EXISTS usuarios;

-- Crear tabla usuarios con todos los campos del modelo
CREATE TABLE usuarios (
    -- Campo ID auto-incremental como clave primaria
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Campos principales del usuario
    nombre VARCHAR(50) NOT NULL COMMENT 'Nombre del usuario',
    apellido VARCHAR(50) NOT NULL COMMENT 'Apellido del usuario',
    correo VARCHAR(100) NOT NULL UNIQUE COMMENT 'Correo electrónico único',
    telefono VARCHAR(20) NOT NULL COMMENT 'Teléfono del usuario',
    clave VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada en MD5',
    
    -- Campos de sistema
    roll VARCHAR(20) DEFAULT 'Usuario' COMMENT 'Rol del usuario: ADMIN, EMPLEADO, Usuario',
    estado VARCHAR(20) DEFAULT 'Inactivo' COMMENT 'Estado del usuario: Activo/Inactivo',
    
    -- Campos de verificación y autenticación
    codigoverificacion VARCHAR(100) DEFAULT NULL COMMENT 'Código de verificación',
    modoAutenticacion VARCHAR(20) DEFAULT 'AUTOREGISTRO' COMMENT 'Modo de autenticación',
    
    -- Campos de auditoría
    fechacreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de registro',
    direccionIP VARCHAR(50) DEFAULT NULL COMMENT 'Dirección IP de registro',
    
    -- Índices para mejorar rendimiento
    INDEX idx_correo (correo),
    INDEX idx_telefono (telefono),
    INDEX idx_estado (estado),
    INDEX idx_roll (roll),
    INDEX idx_fechacreacion (fechacreacion)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='Tabla de usuarios del sistema de reservas de hotel';

-- ==============================================================
-- DATOS DE PRUEBA
-- ==============================================================

-- Insertar usuarios de prueba con contraseñas encriptadas en MD5
INSERT INTO usuarios (nombre, apellido, correo, telefono, clave, roll, estado, codigoverificacion, direccionIP) VALUES 
-- Admin: admin@hotel.com / admin123 (MD5: 0192023a7bbd73250516f069df18b500)
('ADMINISTRADOR', 'SISTEMA', 'admin@hotel.com', '1234567890', '0192023a7bbd73250516f069df18b500', 'ADMIN', 'Activo', '123456', '127.0.0.1'),

-- Empleado: maria.lopez@hotel.com / emp123
('MARIA', 'LOPEZ', 'maria.lopez@hotel.com', '2345678901', 'f4d2e56b3a31b7e1a7e61b8bb7c4b8b4', 'EMPLEADO', 'Activo', '234567', '127.0.0.1'),

-- Cliente: juan.perez@email.com / cliente123
('JUAN', 'PEREZ', 'juan.perez@email.com', '3456789012', '7b7bc2512ee1fedcd76bdc68926d4f7b', 'Usuario', 'Activo', '345678', '192.168.1.100'),

-- Cliente de prueba: test@hotel.com / 123456 (MD5: e10adc3949ba59abbe56e057f20f883e)
('USUARIO', 'PRUEBA', 'test@hotel.com', '1234567890', 'e10adc3949ba59abbe56e057f20f883e', 'Usuario', 'Activo', '456789', '192.168.1.101'),

-- Más usuarios de prueba
('ANA', 'GARCIA', 'ana.garcia@email.com', '4567890123', '5d41402abc4b2a76b9719d911017c592', 'Usuario', 'Inactivo', '567890', '192.168.1.102'),
('CARLOS', 'RODRIGUEZ', 'carlos.rodriguez@email.com', '5678901234', '098f6bcd4621d373cade4e832627b4f6', 'Usuario', 'Inactivo', '678901', '192.168.1.103');

-- ==============================================================
-- VISTAS ÚTILES
-- ==============================================================

-- Vista de usuarios activos
CREATE VIEW usuarios_activos AS
SELECT 
    id,
    nombre,
    apellido,
    correo,
    telefono,
    roll,
    fechacreacion
FROM usuarios 
WHERE estado = 'Activo'
ORDER BY fechacreacion DESC;

-- Vista de estadísticas de usuarios
CREATE VIEW estadisticas_usuarios AS
SELECT 
    COUNT(*) as total_usuarios,
    SUM(CASE WHEN estado = 'Activo' THEN 1 ELSE 0 END) as usuarios_activos,
    SUM(CASE WHEN estado = 'Inactivo' THEN 1 ELSE 0 END) as usuarios_inactivos,
    SUM(CASE WHEN roll = 'ADMIN' THEN 1 ELSE 0 END) as administradores,
    SUM(CASE WHEN roll = 'EMPLEADO' THEN 1 ELSE 0 END) as empleados,
    SUM(CASE WHEN roll = 'Usuario' THEN 1 ELSE 0 END) as usuarios
FROM usuarios;

-- ==============================================================
-- PROCEDIMIENTOS ALMACENADOS
-- ==============================================================

-- Procedimiento para verificar credenciales de login
DELIMITER //
CREATE PROCEDURE VerificarLogin(
    IN p_correo VARCHAR(100),
    IN p_clave_md5 VARCHAR(255)
)
BEGIN
    SELECT 
        id,
        nombre,
        apellido,
        correo,
        telefono,
        roll,
        estado,
        fechacreacion
    FROM usuarios 
    WHERE correo = p_correo 
      AND clave = p_clave_md5 
      AND estado = 'Activo';
END //
DELIMITER ;

-- Procedimiento para contar registros por día
DELIMITER //
CREATE PROCEDURE RegistrosPorDia(
    IN fecha_inicio DATE,
    IN fecha_fin DATE
)
BEGIN
    SELECT 
        DATE(fechacreacion) as fecha,
        COUNT(*) as registros,
        SUM(CASE WHEN roll = 'Usuario' THEN 1 ELSE 0 END) as usuarios,
        SUM(CASE WHEN roll = 'EMPLEADO' THEN 1 ELSE 0 END) as empleados
    FROM usuarios 
    WHERE DATE(fechacreacion) BETWEEN fecha_inicio AND fecha_fin
    GROUP BY DATE(fechacreacion)
    ORDER BY fecha;
END //
DELIMITER ;

-- ==============================================================
-- TRIGGERS PARA AUDITORÍA
-- ==============================================================

-- Trigger para log de nuevos registros
DELIMITER //
CREATE TRIGGER log_nuevo_usuario 
    AFTER INSERT ON usuarios
    FOR EACH ROW
BEGIN
    INSERT INTO log_usuarios (
        accion, 
        id_usuario, 
        correo, 
        fecha_accion, 
        direccion_ip
    ) VALUES (
        'REGISTRO', 
        NEW.id, 
        NEW.correo, 
        NOW(), 
        NEW.direccionIP
    );
END //
DELIMITER ;

-- Crear tabla de logs (si no existe)
CREATE TABLE IF NOT EXISTS log_usuarios (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    accion VARCHAR(20) NOT NULL,
    id_usuario INT,
    correo VARCHAR(150),
    fecha_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    direccion_ip VARCHAR(50),
    INDEX idx_fecha_accion (fecha_accion),
    INDEX idx_accion (accion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================================================
-- CONSULTAS DE VERIFICACIÓN
-- ==============================================================

-- Mostrar estructura de la tabla
DESCRIBE usuarios;

-- Mostrar todos los usuarios
SELECT * FROM usuarios ORDER BY fecha_creacion DESC;

-- Mostrar estadísticas
SELECT * FROM estadisticas_usuarios;

-- Verificar usuarios activos
SELECT * FROM usuarios_activos LIMIT 10;

-- ==============================================================
-- INFORMACIÓN DEL SCRIPT
-- ==============================================================
SELECT 
    'Script de BD ejecutado exitosamente' as mensaje,
    NOW() as fecha_ejecucion,
    DATABASE() as base_datos_actual;