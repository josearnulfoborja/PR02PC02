<<<<<<< HEAD
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
=======
-- ===================================================================
-- SCRIPT SQL PARA SISTEMA DE RESERVAS DE HOTEL
-- Base de datos: plataforma
-- Tabla: usuarios
-- ===================================================================

-- Eliminar la base de datos si existe (opcional para desarrollo)
-- DROP DATABASE IF EXISTS plataforma;

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS plataforma 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
>>>>>>> dev

-- Usar la base de datos
USE plataforma;

<<<<<<< HEAD
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
=======
-- ===================================================================
-- TABLA: usuarios
-- ===================================================================

-- Eliminar la tabla si existe (opcional para desarrollo)
DROP TABLE IF EXISTS usuarios;

-- Crear la tabla usuarios
CREATE TABLE usuarios (
    -- Identificador único del usuario
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Información personal básica
    nombre VARCHAR(50) NOT NULL COMMENT 'Nombre del usuario',
    apellido VARCHAR(50) NOT NULL COMMENT 'Apellido del usuario',
    
    -- Credenciales de acceso
    clave VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada en MD5',
    correo VARCHAR(100) NOT NULL UNIQUE COMMENT 'Correo electrónico único',
    telefono VARCHAR(20) NOT NULL COMMENT 'Número de teléfono',
    
    -- Información del sistema
    roll ENUM('ADMIN', 'CLIENTE', 'EMPLEADO', 'GERENTE') DEFAULT 'CLIENTE' 
        COMMENT 'Rol del usuario en el sistema',
    estado BOOLEAN DEFAULT TRUE COMMENT 'Estado activo/inactivo del usuario',
    
    -- Verificación y autenticación
    codigo_verificacion VARCHAR(10) COMMENT 'Código para verificación de cuenta',
    modo_autenticacion ENUM('EMAIL', 'SMS', '2FA') DEFAULT 'EMAIL' 
        COMMENT 'Método de autenticación preferido',
    
    -- Información de auditoría
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP 
        COMMENT 'Fecha y hora de registro',
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 
        COMMENT 'Fecha y hora de última modificación',
    direccion_ip VARCHAR(45) COMMENT 'Dirección IP desde donde se registró',
    
    -- Índices para optimización
>>>>>>> dev
    INDEX idx_correo (correo),
    INDEX idx_telefono (telefono),
    INDEX idx_estado (estado),
    INDEX idx_roll (roll),
<<<<<<< HEAD
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
=======
    INDEX idx_fecha_creacion (fecha_creacion)
) ENGINE=InnoDB 
  AUTO_INCREMENT=1000 
  COMMENT='Tabla para almacenar información de usuarios del sistema de reservas';

-- ===================================================================
-- INSERTAR DATOS DE PRUEBA
-- ===================================================================

-- Usuario administrador por defecto
INSERT INTO usuarios (
    nombre, apellido, clave, correo, telefono, roll, estado,
    codigo_verificacion, modo_autenticacion, direccion_ip
) VALUES (
    'ADMINISTRADOR',
    'SISTEMA', 
    MD5('admin123'),  -- Contraseña: admin123
    'admin@hotel.com',
    '1234567890',
    'ADMIN',
    TRUE,
    '000000',
    'EMAIL',
    '127.0.0.1'
);

-- Usuario cliente de prueba
INSERT INTO usuarios (
    nombre, apellido, clave, correo, telefono, roll, estado,
    codigo_verificacion, modo_autenticacion, direccion_ip
) VALUES (
    'JUAN CARLOS',
    'PÉREZ GARCÍA',
    MD5('cliente123'),  -- Contraseña: cliente123
    'juan.perez@email.com',
    '9876543210',
    'CLIENTE',
    TRUE,
    '123456',
    'EMAIL',
    '192.168.1.1'
);

-- Usuario empleado de prueba
INSERT INTO usuarios (
    nombre, apellido, clave, correo, telefono, roll, estado,
    codigo_verificacion, modo_autenticacion, direccion_ip
) VALUES (
    'MARÍA FERNANDA',
    'LÓPEZ MARTÍNEZ',
    MD5('empleado123'),  -- Contraseña: empleado123
    'maria.lopez@hotel.com',
    '5555666677',
    'EMPLEADO',
    TRUE,
    '789012',
    'EMAIL',
    '192.168.1.100'
);

-- ===================================================================
-- CONSULTAS DE VERIFICACIÓN
-- ===================================================================

-- Mostrar estructura de la tabla
DESCRIBE usuarios;

-- Mostrar todos los usuarios registrados
SELECT 
    id_usuario,
>>>>>>> dev
    nombre,
    apellido,
    correo,
    telefono,
    roll,
<<<<<<< HEAD
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
=======
    estado,
    fecha_creacion
FROM usuarios
ORDER BY fecha_creacion DESC;

-- Contar usuarios por rol
SELECT 
    roll,
    COUNT(*) as total_usuarios
FROM usuarios 
WHERE estado = TRUE
GROUP BY roll;

-- ===================================================================
-- FUNCIONES Y PROCEDIMIENTOS ÚTILES
-- ===================================================================

-- Procedimiento para verificar un usuario
DELIMITER //
CREATE PROCEDURE VerificarUsuario(
    IN p_correo VARCHAR(100),
    IN p_clave VARCHAR(255)
)
BEGIN
    DECLARE usuario_existe INT DEFAULT 0;
    
    SELECT COUNT(*) INTO usuario_existe
    FROM usuarios 
    WHERE correo = p_correo 
      AND clave = p_clave 
      AND estado = TRUE;
    
    IF usuario_existe > 0 THEN
        SELECT 
            id_usuario,
            nombre,
            apellido,
            correo,
            roll,
            'Login exitoso' as mensaje
        FROM usuarios 
        WHERE correo = p_correo 
          AND clave = p_clave 
          AND estado = TRUE;
    ELSE
        SELECT 
            0 as id_usuario,
            '' as nombre,
            '' as apellido,
            '' as correo,
            '' as roll,
            'Credenciales inválidas' as mensaje;
    END IF;
END //
DELIMITER ;

-- Función para contar usuarios activos
DELIMITER //
CREATE FUNCTION ContarUsuariosActivos() 
RETURNS INT
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE total INT DEFAULT 0;
    SELECT COUNT(*) INTO total 
    FROM usuarios 
    WHERE estado = TRUE;
    RETURN total;
END //
DELIMITER ;

-- ===================================================================
-- TRIGGERS PARA AUDITORÍA
-- ===================================================================

-- Trigger para registrar cambios en usuarios
CREATE TABLE IF NOT EXISTS usuarios_auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    accion ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    campo_modificado VARCHAR(50),
    valor_anterior TEXT,
    valor_nuevo TEXT,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_modificador VARCHAR(100),
    ip_modificador VARCHAR(45)
);

-- Trigger después de insertar
DELIMITER //
CREATE TRIGGER tr_usuarios_insert
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO usuarios_auditoria (
        id_usuario, accion, campo_modificado, 
        valor_nuevo, usuario_modificador
    ) VALUES (
        NEW.id_usuario, 'INSERT', 'registro_completo',
        CONCAT('Usuario: ', NEW.nombre, ' ', NEW.apellido, ' - Email: ', NEW.correo),
        USER()
>>>>>>> dev
    );
END //
DELIMITER ;

<<<<<<< HEAD
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
=======
-- Trigger después de actualizar
DELIMITER //
CREATE TRIGGER tr_usuarios_update
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
    -- Registrar cambio en nombre
    IF OLD.nombre != NEW.nombre THEN
        INSERT INTO usuarios_auditoria (
            id_usuario, accion, campo_modificado, 
            valor_anterior, valor_nuevo, usuario_modificador
        ) VALUES (
            NEW.id_usuario, 'UPDATE', 'nombre',
            OLD.nombre, NEW.nombre, USER()
        );
    END IF;
    
    -- Registrar cambio en estado
    IF OLD.estado != NEW.estado THEN
        INSERT INTO usuarios_auditoria (
            id_usuario, accion, campo_modificado, 
            valor_anterior, valor_nuevo, usuario_modificador
        ) VALUES (
            NEW.id_usuario, 'UPDATE', 'estado',
            OLD.estado, NEW.estado, USER()
        );
    END IF;
END //
DELIMITER ;

-- ===================================================================
-- ÍNDICES ADICIONALES PARA RENDIMIENTO
-- ===================================================================

-- Índice compuesto para login
CREATE INDEX idx_login ON usuarios(correo, clave, estado);

-- Índice para búsquedas por nombre completo
CREATE INDEX idx_nombre_completo ON usuarios(nombre, apellido);

-- Índice para auditoría por fecha
CREATE INDEX idx_auditoria_fecha ON usuarios_auditoria(fecha_modificacion);

-- ===================================================================
-- VISTAS ÚTILES
-- ===================================================================

-- Vista de usuarios activos
CREATE VIEW v_usuarios_activos AS
SELECT 
    id_usuario,
    CONCAT(nombre, ' ', apellido) as nombre_completo,
    correo,
    telefono,
    roll,
    fecha_creacion,
    DATEDIFF(NOW(), fecha_creacion) as dias_registrado
FROM usuarios 
WHERE estado = TRUE
ORDER BY fecha_creacion DESC;

-- Vista de estadísticas de usuarios
CREATE VIEW v_estadisticas_usuarios AS
SELECT 
    COUNT(*) as total_usuarios,
    COUNT(CASE WHEN estado = TRUE THEN 1 END) as usuarios_activos,
    COUNT(CASE WHEN estado = FALSE THEN 1 END) as usuarios_inactivos,
    COUNT(CASE WHEN roll = 'ADMIN' THEN 1 END) as administradores,
    COUNT(CASE WHEN roll = 'CLIENTE' THEN 1 END) as clientes,
    COUNT(CASE WHEN roll = 'EMPLEADO' THEN 1 END) as empleados,
    COUNT(CASE WHEN DATE(fecha_creacion) = CURDATE() THEN 1 END) as registros_hoy
FROM usuarios;

-- ===================================================================
-- CONSULTAS DE VERIFICACIÓN FINAL
-- ===================================================================

-- Verificar que todo se creó correctamente
SELECT 'Base de datos creada exitosamente' as estado;
SELECT 'Tabla usuarios creada con índices' as estructura;
SELECT 'Datos de prueba insertados' as datos;
SELECT 'Procedimientos y funciones creados' as funciones;
SELECT 'Triggers de auditoría activados' as auditoria;
SELECT 'Vistas creadas para consultas' as vistas;

-- Mostrar estadísticas finales
SELECT * FROM v_estadisticas_usuarios;

-- ===================================================================
-- INSTRUCCIONES DE USO
-- ===================================================================

/*
INSTRUCCIONES PARA USAR ESTE SCRIPT:

1. EJECUTAR EN MYSQL:
   - Abrir MySQL Workbench o línea de comandos
   - Ejecutar este script completo
   - Verificar que no haya errores

2. CONFIGURAR CONEXIÓN:
   - En Conexion.java, verificar:
     * URL: jdbc:mysql://localhost:3306/plataforma
     * Usuario: root (o su usuario de MySQL)
     * Password: (configurar según su instalación)

3. CREDENCIALES DE PRUEBA:
   - Admin: admin@hotel.com / admin123
   - Cliente: juan.perez@email.com / cliente123
   - Empleado: maria.lopez@hotel.com / empleado123

4. VERIFICAR FUNCIONAMIENTO:
   - Ejecutar Conexion.main() para probar conexión
   - Usar VerificarUsuario() para probar login
   - Consultar v_usuarios_activos para ver datos

5. EN PRODUCCIÓN:
   - Cambiar contraseñas por defecto
   - Configurar contraseña de base de datos
   - Revisar permisos de usuario MySQL
   - Activar logs de auditoría
*/
>>>>>>> dev
