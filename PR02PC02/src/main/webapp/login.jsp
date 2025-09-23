<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Reservas</title>
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
            max-width: 450px;
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
        }
        
        .back-button:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
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
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        .form-group input.error {
            border-color: #e74c3c;
            background-color: #ffeaea;
        }
        
        .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }
        
        .btn {
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
            margin-bottom: 15px;
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn:active {
            transform: translateY(0);
        }
        
        .btn-secondary {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
        }
        
        .divider {
            text-align: center;
            margin: 20px 0;
            position: relative;
        }
        
        .divider::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            height: 1px;
            background: #ddd;
        }
        
        .divider span {
            background: white;
            padding: 0 15px;
            color: #666;
        }
        
        .forgot-password {
            text-align: center;
            margin-top: 15px;
        }
        
        .forgot-password a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
        }
        
        .forgot-password a:hover {
            text-decoration: underline;
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
        
        .loading {
            display: none;
            text-align: center;
            margin-top: 10px;
        }
        
        .loading span {
            display: inline-block;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: #667eea;
            margin: 0 2px;
            animation: loading 1.4s infinite ease-in-out;
        }
        
        .loading span:nth-child(1) { animation-delay: -0.32s; }
        .loading span:nth-child(2) { animation-delay: -0.16s; }
        
        @keyframes loading {
            0%, 80%, 100% {
                transform: scale(0);
            }
            40% {
                transform: scale(1);
            }
        }
        
        @media (max-width: 768px) {
            .container {
                margin: 10px;
                padding: 30px 20px;
            }
            
            .header h1 {
                font-size: 1.8em;
            }
            
            .back-button {
                position: static;
                margin-bottom: 20px;
                display: inline-block;
            }
        }
    </style>
</head>
<body>
    <a href="index.html" class="back-button">← Volver al Inicio</a>
    
    <div class="container">
        <div class="header">
            <h1>🏨 Iniciar Sesión</h1>
            <p>Accede a tu cuenta para continuar</p>
        </div>
        
        <form id="formLogin">
            <div class="form-group">
                <label for="correoLogin">Correo Electrónico</label>
                <input type="email" id="correoLogin" name="correo" required placeholder="tu@email.com">
                <div class="error-message" id="errorCorreoLogin"></div>
            </div>
            
            <div class="form-group">
                <label for="claveLogin">Contraseña</label>
                <input type="password" id="claveLogin" name="clave" required placeholder="Tu contraseña">
                <div class="error-message" id="errorClaveLogin"></div>
            </div>
            
            <button type="button" class="btn" id="btnLogin">
                <span id="btnText">INICIAR SESIÓN</span>
                <div class="loading" id="loadingIndicator">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </button>
            
            <div class="forgot-password">
                <a href="#" onclick="alert('Funcionalidad próximamente disponible')">¿Olvidaste tu contraseña?</a>
            </div>
            
            <div class="divider">
                <span>¿No tienes cuenta?</span>
            </div>
            
            <button type="button" class="btn btn-secondary" id="btnRegistrarse">CREAR CUENTA</button>
        </form>
        
        <div id="resultado"></div>
    </div>

    <script>
        // Variables globales
        let isLoading = false;
        
        // Elementos del DOM
        const btnLogin = document.getElementById('btnLogin');
        const btnText = document.getElementById('btnText');
        const loadingIndicator = document.getElementById('loadingIndicator');
        const correoInput = document.getElementById('correoLogin');
        const claveInput = document.getElementById('claveLogin');
        
        // Eventos
        btnLogin.addEventListener('click', iniciarSesion);
        document.getElementById('btnRegistrarse').addEventListener('click', () => {
            window.location.href = 'registro.jsp';
        });
        
        // Permitir login con Enter
        document.getElementById('formLogin').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                if (!isLoading) {
                    iniciarSesion();
                }
            }
        });
        
        // Función para mostrar/ocultar loading
        function toggleLoading(show) {
            isLoading = show;
            if (show) {
                btnText.style.display = 'none';
                loadingIndicator.style.display = 'block';
                btnLogin.disabled = true;
                btnLogin.style.opacity = '0.7';
            } else {
                btnText.style.display = 'inline';
                loadingIndicator.style.display = 'none';
                btnLogin.disabled = false;
                btnLogin.style.opacity = '1';
            }
        }
        
        // Función para iniciar sesión
        async function iniciarSesion() {
            if (isLoading) return;
            
            limpiarErrores();
            
            const correo = correoInput.value.trim();
            const clave = claveInput.value;
            
            // Validaciones básicas
            if (!correo) {
                mostrarError('errorCorreoLogin', 'El correo es obligatorio');
                correoInput.focus();
                return;
            }
            
            if (!validarEmail(correo)) {
                mostrarError('errorCorreoLogin', 'Por favor ingresa un correo válido');
                correoInput.focus();
                return;
            }
            
            if (!clave) {
                mostrarError('errorClaveLogin', 'La contraseña es obligatoria');
                claveInput.focus();
                return;
            }
            
            if (clave.length < 6) {
                mostrarError('errorClaveLogin', 'La contraseña debe tener al menos 6 caracteres');
                claveInput.focus();
                return;
            }
            
            // Mostrar loading
            toggleLoading(true);
            
            try {
                const response = await fetch('SrvLogin', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify({
                        correo: correo,
                        clave: clave
                    })
                });
                
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                
                const data = await response.json();
                
                if (data.estado) {
                    // Login exitoso
                    mostrarResultado('¡Bienvenido ' + (data.usuario ? data.usuario.nombre : '') + '!', true);
                    
                    // Guardar información del usuario en localStorage (opcional)
                    if (data.usuario) {
                        localStorage.setItem('usuario', JSON.stringify(data.usuario));
                    }
                    
                    // Redireccionar después de 2 segundos
                    setTimeout(() => {
                        window.location.href = 'dashboard.jsp'; // O la página principal para usuarios logueados
                    }, 2000);
                } else {
                    mostrarResultado(data.mensaje || 'Credenciales inválidas', false);
                }
                
            } catch (error) {
                console.error('Error:', error);
                mostrarResultado('Error de conexión con el servidor. Intenta nuevamente.', false);
            } finally {
                toggleLoading(false);
            }
        }
        
        // Función para validar email
        function validarEmail(email) {
            const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
            return regex.test(email);
        }
        
        // Función para mostrar errores
        function mostrarError(campo, mensaje) {
            const input = document.getElementById(campo.replace('error', '').replace('Login', 'Login'));
            const errorDiv = document.getElementById(campo);
            
            if (input) {
                input.classList.add('error');
                input.addEventListener('input', function() {
                    this.classList.remove('error');
                    errorDiv.style.display = 'none';
                }, { once: true });
            }
            
            if (errorDiv) {
                errorDiv.textContent = mensaje;
                errorDiv.style.display = 'block';
            }
        }
        
        // Función para limpiar errores
        function limpiarErrores() {
            const inputs = document.querySelectorAll('input');
            const errors = document.querySelectorAll('.error-message');
            
            inputs.forEach(input => input.classList.remove('error'));
            errors.forEach(error => {
                error.style.display = 'none';
                error.textContent = '';
            });
            
            // Ocultar resultado anterior
            const resultado = document.getElementById('resultado');
            resultado.style.display = 'none';
        }
        
        // Función para mostrar resultado
        function mostrarResultado(mensaje, esExito) {
            const resultado = document.getElementById('resultado');
            resultado.textContent = mensaje;
            resultado.className = esExito ? 'success' : 'error';
            resultado.style.display = 'block';
            
            // Auto-ocultar después de 5 segundos si es un error
            if (!esExito) {
                setTimeout(() => {
                    if (resultado.style.display === 'block') {
                        resultado.style.display = 'none';
                    }
                }, 5000);
            }
        }
        
        // Verificar si hay un mensaje de registro exitoso en la URL
        window.addEventListener('load', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const registroExitoso = urlParams.get('registro');
            const logoutExitoso = urlParams.get('logout');
            
            if (registroExitoso === 'exitoso') {
                mostrarResultado('¡Registro exitoso! Ahora puedes iniciar sesión', true);
                // Limpiar la URL
                window.history.replaceState({}, document.title, window.location.pathname);
            }
            
            if (logoutExitoso === 'exitoso') {
                mostrarResultado('Has cerrado sesión correctamente', true);
                // Limpiar la URL
                window.history.replaceState({}, document.title, window.location.pathname);
            }
            
            // Enfocar el campo de correo
            correoInput.focus();
        });
        
        // Funcionalidad adicional: recordar email (opcional)
        document.addEventListener('DOMContentLoaded', function() {
            const rememberedEmail = localStorage.getItem('rememberedEmail');
            if (rememberedEmail) {
                correoInput.value = rememberedEmail;
                claveInput.focus();
            }
            
            // Guardar email cuando se ingrese (opcional)
            correoInput.addEventListener('blur', function() {
                if (this.value && validarEmail(this.value)) {
                    localStorage.setItem('rememberedEmail', this.value);
                }
            });
        });
    </script>
</body>
</html>