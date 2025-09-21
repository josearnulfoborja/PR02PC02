<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Usuario - Sistema de Reservas de Hotel</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
        }
        
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .header h1 {
            color: #333;
            font-size: 2.2em;
            margin-bottom: 10px;
        }
        
        .header p {
            color: #666;
            font-size: 1.1em;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: bold;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .form-group input.error {
            border-color: #e74c3c;
            background-color: #ffeaea;
        }
        
        .captcha-container {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
        }
        
        .captcha-text {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            letter-spacing: 3px;
            margin-bottom: 10px;
            background: linear-gradient(45deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }
        
        .btn-register {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.2s ease;
        }
        
        .btn-register:hover {
            transform: translateY(-2px);
        }
        
        .btn-register:active {
            transform: translateY(0);
        }
        
        .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }
        
        .success-message {
            color: #27ae60;
            font-size: 14px;
            margin-top: 5px;
        }
        
        #resultado {
            margin-top: 20px;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
            font-weight: bold;
            display: none;
        }
        
        #resultado.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        #resultado.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f1b0b7;
        }
        
        .required {
            color: #e74c3c;
        }
        
        .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            padding: 10px 20px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            backdrop-filter: blur(10px);
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
        }
        
        .back-button:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }
        
        @media (max-width: 768px) {
            .back-button {
                position: static;
                margin-bottom: 20px;
                display: inline-flex;
                align-self: flex-start;
            }
            
            .container {
                margin-top: 20px;
            }
        }
    </style>
</head>
<body>
    <a href="index.html" class="back-button">
        <span>←</span> Volver al Inicio
    </a>
    
    <div class="container">
        <div class="header">
            <h1>Registro de Usuario</h1>
            <p>Sistema de Reservas de Hotel</p>
        </div>
        
        <form id="registroForm">
            <div class="form-group">
                <label for="nombre">Nombre <span class="required">*</span></label>
                <input type="text" id="nombre" name="nombre" required>
                <div class="error-message" id="errorNombre"></div>
            </div>
            
            <div class="form-group">
                <label for="apellido">Apellido <span class="required">*</span></label>
                <input type="text" id="apellido" name="apellido" required>
                <div class="error-message" id="errorApellido"></div>
            </div>
            
            <div class="form-group">
                <label for="correo">Correo Electrónico <span class="required">*</span></label>
                <input type="email" id="correo" name="correo" required>
                <div class="error-message" id="errorCorreo"></div>
            </div>
            
            <div class="form-group">
                <label for="telefono">Teléfono <span class="required">*</span></label>
                <input type="tel" id="telefono" name="telefono" required>
                <div class="error-message" id="errorTelefono"></div>
            </div>
            
            <div class="form-group">
                <label for="clave">Contraseña <span class="required">*</span></label>
                <input type="password" id="clave" name="clave" required>
                <div class="error-message" id="errorClave"></div>
            </div>
            
            <div class="form-group">
                <label for="confirmarClave">Confirmar Contraseña <span class="required">*</span></label>
                <input type="password" id="confirmarClave" name="confirmarClave" required>
                <div class="error-message" id="errorConfirmarClave"></div>
            </div>
            
            <div class="captcha-container">
                <div class="captcha-text">H7B2K9</div>
                <label for="captcha">Ingrese el código mostrado arriba:</label>
                <input type="text" id="captcha" name="captcha" placeholder="H7B2K9" style="margin-top: 10px;">
                <div class="error-message" id="errorCaptcha"></div>
            </div>
            
            <button type="button" class="btn-register" id="btnRegistrar">REGISTRARSE</button>
            
            <div style="text-align: center; margin-top: 20px;">
                <a href="index.html" style="color: #667eea; text-decoration: none; font-weight: bold;">
                    ← Volver al Inicio
                </a>
                <span style="margin: 0 15px; color: #ccc;">|</span>
                <a href="login.jsp" style="color: #667eea; text-decoration: none; font-weight: bold;">
                    ¿Ya tienes cuenta? Inicia sesión
                </a>
            </div>
        </form>
        
        <div id="resultado"></div>
    </div>

    <script>
        /**
         * Clase Usuario para manejar los datos del usuario y validaciones
         */
        class Usuario {
            constructor() {
                this.nombre = '';
                this.apellido = '';
                this.correo = '';
                this.telefono = '';
                this.clave = '';
                this.mensaje = '';
                this.estado = true;
            }
            
            /**
             * Convierte el nombre a mayúsculas y elimina espacios
             */
            formatearNombre() {
                this.nombre = this.nombre.trim().toUpperCase();
                return this;
            }
            
            /**
             * Convierte el apellido a mayúsculas y elimina espacios
             */
            formatearApellido() {
                this.apellido = this.apellido.trim().toUpperCase();
                return this;
            }
            
            /**
             * Elimina espacios al inicio y final de todos los campos
             */
            eliminarEspacios() {
                this.nombre = this.nombre.trim();
                this.apellido = this.apellido.trim();
                this.correo = this.correo.trim();
                this.telefono = this.telefono.trim();
                return this;
            }
            
            /**
             * Valida que el nombre no contenga números
             */
            validarNombre() {
                const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
                if (!this.nombre || this.nombre.length < 2) {
                    this.mensaje = 'El nombre debe tener al menos 2 caracteres';
                    return false;
                }
                if (!regex.test(this.nombre)) {
                    this.mensaje = 'El nombre no debe contener números ni caracteres especiales';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida que el apellido no contenga números
             */
            validarApellido() {
                const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
                if (!this.apellido || this.apellido.length < 2) {
                    this.mensaje = 'El apellido debe tener al menos 2 caracteres';
                    return false;
                }
                if (!regex.test(this.apellido)) {
                    this.mensaje = 'El apellido no debe contener números ni caracteres especiales';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida el formato del correo electrónico
             */
            validarCorreo() {
                const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
                if (!this.correo) {
                    this.mensaje = 'El correo electrónico es obligatorio';
                    return false;
                }
                if (!regex.test(this.correo)) {
                    this.mensaje = 'El formato del correo electrónico no es válido';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida el formato del teléfono
             */
            validarTelefono() {
                const regex = /^[0-9]{8,15}$/;
                if (!this.telefono) {
                    this.mensaje = 'El teléfono es obligatorio';
                    return false;
                }
                if (!regex.test(this.telefono.replace(/[\s\-\+\(\)]/g, ''))) {
                    this.mensaje = 'El teléfono debe contener entre 8 y 15 dígitos';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida la contraseña
             */
            validarClave() {
                if (!this.clave || this.clave.length < 6) {
                    this.mensaje = 'La contraseña debe tener al menos 6 caracteres';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida que las contraseñas coincidan
             */
            validarConfirmacionClave(confirmarClave) {
                if (this.clave !== confirmarClave) {
                    this.mensaje = 'Las contraseñas no coinciden';
                    return false;
                }
                return true;
            }
            
            /**
             * Valida todos los campos del usuario
             */
            validarTodos(confirmarClave, captcha) {
                this.eliminarEspacios();
                
                if (!this.validarNombre()) return false;
                if (!this.validarApellido()) return false;
                if (!this.validarCorreo()) return false;
                if (!this.validarTelefono()) return false;
                if (!this.validarClave()) return false;
                if (!this.validarConfirmacionClave(confirmarClave)) return false;
                
                // Validar captcha
                if (captcha !== 'H7B2K9') {
                    this.mensaje = 'El código captcha no es correcto';
                    return false;
                }
                
                return true;
            }
            
            /**
             * Convierte el objeto a JSON para enviar al servidor
             */
            toJSON() {
                return {
                    nombre: this.nombre,
                    apellido: this.apellido,
                    correo: this.correo,
                    telefono: this.telefono,
                    clave: this.clave,
                    estado: this.estado
                };
            }
        }
        
        /**
         * Función para mostrar errores en los campos
         */
        function mostrarError(campo, mensaje) {
            const input = document.getElementById(campo);
            const errorDiv = document.getElementById('error' + campo.charAt(0).toUpperCase() + campo.slice(1));
            
            input.classList.add('error');
            errorDiv.textContent = mensaje;
            errorDiv.style.display = 'block';
        }
        
        /**
         * Función para limpiar errores
         */
        function limpiarErrores() {
            const inputs = document.querySelectorAll('input');
            const errors = document.querySelectorAll('.error-message');
            
            inputs.forEach(input => input.classList.remove('error'));
            errors.forEach(error => {
                error.style.display = 'none';
                error.textContent = '';
            });
        }
        
        /**
         * Función para mostrar resultado del registro
         */
        function mostrarResultado(mensaje, esExito) {
            const resultado = document.getElementById('resultado');
            resultado.textContent = mensaje;
            resultado.className = esExito ? 'success' : 'error';
            resultado.style.display = 'block';
            
            // Ocultar después de 5 segundos si es éxito
            if (esExito) {
                setTimeout(() => {
                    resultado.style.display = 'none';
                    document.getElementById('registroForm').reset();
                }, 5000);
            }
        }
        
        /**
         * Arrow function para manejar el registro de usuario
         */
        const registrarUsuario = () => {
            limpiarErrores();
            
            // Crear instancia del usuario
            const usuario = new Usuario();
            
            // Obtener valores del formulario
            usuario.nombre = document.getElementById('nombre').value;
            usuario.apellido = document.getElementById('apellido').value;
            usuario.correo = document.getElementById('correo').value;
            usuario.telefono = document.getElementById('telefono').value;
            usuario.clave = document.getElementById('clave').value;
            const confirmarClave = document.getElementById('confirmarClave').value;
            const captcha = document.getElementById('captcha').value;
            
            // Validar campos
            if (!usuario.validarTodos(confirmarClave, captcha)) {
                // Mostrar error específico según el campo que falló
                if (usuario.mensaje.includes('nombre')) {
                    mostrarError('nombre', usuario.mensaje);
                } else if (usuario.mensaje.includes('apellido')) {
                    mostrarError('apellido', usuario.mensaje);
                } else if (usuario.mensaje.includes('correo')) {
                    mostrarError('correo', usuario.mensaje);
                } else if (usuario.mensaje.includes('teléfono')) {
                    mostrarError('telefono', usuario.mensaje);
                } else if (usuario.mensaje.includes('contraseña')) {
                    if (usuario.mensaje.includes('coinciden')) {
                        mostrarError('confirmarClave', usuario.mensaje);
                    } else {
                        mostrarError('clave', usuario.mensaje);
                    }
                } else if (usuario.mensaje.includes('captcha')) {
                    mostrarError('captcha', usuario.mensaje);
                }
                return;
            }
            
            // Formatear nombre y apellido
            usuario.formatearNombre().formatearApellido();
            
            // Enviar datos al servlet usando Fetch API
            fetch('SrvUsuario', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(usuario.toJSON())
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la respuesta del servidor');
                }
                return response.json();
            })
            .then(data => {
                if (data.estado) {
                    mostrarResultado(data.mensaje || 'Usuario registrado exitosamente', true);
                    // Redireccionar después de 2 segundos
                    setTimeout(() => {
                        if (data.redirect) {
                            window.location.href = data.redirect;
                        } else {
                            window.location.href = 'index.html?registro=exitoso';
                        }
                    }, 2000);
                } else {
                    mostrarResultado(data.mensaje || 'Error al registrar usuario', false);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarResultado('Error de conexión con el servidor', false);
            });
        };
        
        // Asignar evento al botón
        document.getElementById('btnRegistrar').addEventListener('click', registrarUsuario);
        
        // Permitir registro con Enter
        document.getElementById('registroForm').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                registrarUsuario();
            }
        });
    </script>
</body>
</html>