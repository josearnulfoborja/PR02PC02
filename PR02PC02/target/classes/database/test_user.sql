-- ===================================================================
-- SCRIPT PARA INSERTAR USUARIO DE PRUEBA - LOGIN
-- ===================================================================

USE plataforma;

-- Eliminar usuario de prueba si existe (opcional)
DELETE FROM usuarios WHERE correo = 'test@hotel.com';

-- Insertar usuario de prueba simple para el login
INSERT INTO usuarios (
    nombre, 
    apellido, 
    clave, 
    correo, 
    telefono, 
    roll, 
    estado,
    codigo_verificacion, 
    modo_autenticacion, 
    fecha_creacion,
    direccion_ip
) VALUES (
    'USUARIO',
    'PRUEBA', 
    MD5('123456'),  -- Contraseña: 123456
    'test@hotel.com',
    '1234567890',
    'CLIENTE',
    TRUE,
    '000000',
    'EMAIL',
    NOW(),
    '127.0.0.1'
);

-- Verificar que se insertó correctamente
SELECT 
    id_usuario,
    nombre,
    apellido,
    correo,
    clave,
    estado,
    fecha_creacion
FROM usuarios 
WHERE correo = 'test@hotel.com';

-- Mostrar el MD5 de la contraseña para verificar
SELECT MD5('123456') as clave_md5_prueba;

-- ===================================================================
-- INSTRUCCIONES DE USO:
-- ===================================================================
/*
1. Ejecuta este script en MySQL Workbench o línea de comandos
2. Verifica que el usuario se insertó correctamente
3. Intenta hacer login con estas credenciales:
   - Email: test@hotel.com
   - Contraseña: 123456

4. Si el login no funciona, revisa los logs del servidor para ver
   los mensajes de debug que agregamos al servlet.

5. Para depurar manualmente, puedes ejecutar esta consulta:
   SELECT * FROM usuarios WHERE correo = 'test@hotel.com' AND clave = MD5('123456') AND estado = TRUE;
*/