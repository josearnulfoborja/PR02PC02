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

-- Usar la base de datos
USE plataforma;

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
    INDEX idx_correo (correo),
    INDEX idx_telefono (telefono),
    INDEX idx_estado (estado),
    INDEX idx_roll (roll),
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
    nombre,
    apellido,
    correo,
    telefono,
    roll,
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
    );
END //
DELIMITER ;

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