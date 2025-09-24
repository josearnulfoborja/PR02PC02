<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
    <div class="form-container">
        <h2>Registro de Usuario</h2>
    <form id="registroForm">
            <!-- Nombre -->
            <div class="input-group">
                <span class="icon">
                    <!-- icono usuario -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-user">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0"/>
                        <path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2"/>
                    </svg>
                </span>
                <input type="text" placeholder="Nombre" id="nombre" required>
            </div>

            <!-- Apellido -->
            <div class="input-group">
                <span class="icon">
                    <!-- mismo icono usuario -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-user">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M8 7a4 4 0 1 0 8 0a4 4 0 0 0 -8 0"/>
                        <path d="M6 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2"/>
                    </svg>
                </span>
                <input type="text" placeholder="Apellido" id="apellido" required>
            </div>

            <!-- Correo -->
            <div class="input-group">
                <span class="icon">
                    <!-- icono correo -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-mail">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M3 7a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v10a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2v-10z"/>
                        <path d="M3 7l9 6l9 -6"/>
                    </svg>
                </span>
                <input type="email" placeholder="Correo electrónico" id="correo" required>
            </div>

            <!-- Teléfono -->
            <div class="input-group">
                <span class="icon">
                    <!-- icono telefono -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-phone">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M5 4h4l2 5l-2.5 1.5a11 11 0 0 0 5 5l1.5 -2.5l5 2v4a2 2 0 0 1 -2 2a16 16 0 0 1 -15 -15a2 2 0 0 1 2 -2"/>
                    </svg>
                </span>
                <input type="tel" placeholder="Teléfono" id="telefono" required>
            </div>

            <!-- Contraseña -->
            <div class="input-group password-group">
                <span class="icon">
                    <!-- icono clave -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-brand-samsungpass">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M4 10m0 2a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v7a2 2 0 0 1 -2 2h-12a2 2 0 0 1 -2 -2z" />
                        <path d="M7 10v-1.862c0 -2.838 2.239 -5.138 5 -5.138s5 2.3 5 5.138v1.862" />
                        <path d="M10.485 17.577c.337 .29 .7 .423 1.515 .423h.413c.323 0 .633 -.133 .862 -.368a1.27 1.27 0 0 0 .356 -.886c0 -.332 -.128 -.65 -.356 -.886a1.203 1.203 0 0 0 -.862 -.368h-.826a1.2 1.2 0 0 1 -.861 -.367a1.27 1.27 0 0 1 -.356 -.886c0 -.332 .128 -.651 .356 -.886a1.2 1.2 0 0 1 .861 -.368h.413c.816 0 1.178 .133 1.515 .423" />
                    </svg>
                </span>
                <input type="password" id="clave" placeholder="Contraseña" required>
                <span class="toggle-password" title="Mostrar contraseña">
                    <!-- icono ojo (visible por defecto oculto) -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-eye-check">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M10 12a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
                        <path d="M11.102 17.957c-3.204 -.307 -5.904 -2.294 -8.102 -5.957c2.4 -4 5.4 -6 9 -6c3.6 0 6.6 2 9 6a19.5 19.5 0 0 1 -.663 1.032" />
                        <path d="M15 19l2 2l4 -4" />
                    </svg>
                </span>
            </div>


            <!-- Confirmar contraseña -->
            <div class="input-group">
                <span class="icon">
                    <!-- icono clave -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-brand-samsungpass">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M4 10m0 2a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v7a2 2 0 0 1 -2 2h-12a2 2 0 0 1 -2 -2z" />
                        <path d="M7 10v-1.862c0 -2.838 2.239 -5.138 5 -5.138s5 2.3 5 5.138v1.862" />
                        <path d="M10.485 17.577c.337 .29 .7 .423 1.515 .423h.413c.323 0 .633 -.133 .862 -.368a1.27 1.27 0 0 0 .356 -.886c0 -.332 -.128 -.65 -.356 -.886a1.203 1.203 0 0 0 -.862 -.368h-.826a1.2 1.2 0 0 1 -.861 -.367a1.27 1.27 0 0 1 -.356 -.886c0 -.332 .128 -.651 .356 -.886a1.2 1.2 0 0 1 .861 -.368h.413c.816 0 1.178 .133 1.515 .423" />
                    </svg>
                </span>
                <input type="password" placeholder="Confirmar contraseña" id="confirmarClave" required>
            </div>

            <!-- Rol -->
            <div class="input-group">
                <span class="icon">
                    <!-- icono rol -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                        class="icon-tabler-users-group">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                        <path d="M10 13a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
                        <path d="M8 21v-1a2 2 0 0 1 2 -2h4a2 2 0 0 1 2 2v1" />
                        <path d="M15 5a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
                        <path d="M17 10h2a2 2 0 0 1 2 2v1" />
                        <path d="M5 5a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
                        <path d="M3 13v-1a2 2 0 0 1 2 -2h2" />
                    </svg>
                </span>
                <input type="text" placeholder="Rol" value="usuario" readonly>
            </div>

            <!-- Captcha -->
            <!-- <div class="captcha">
                <div class="captcha-text">ABC123</div>
                <input type="text" placeholder="Escriba el captcha" id="captcha" required>
            </div> -->
            <div class="captcha-container">
                <div class="captcha-text">H7B2K9</div>
                <label for="captcha">Ingrese el código mostrado arriba:</label>
                <input type="text" id="captcha" name="captcha" placeholder="H7B2K9" style="margin-top: 10px;">
                <div class="error-message" id="errorCaptcha"></div>
            </div>

            <!-- Botón -->
            <button type="button" id="btnRegistrar">REGISTRARSE</button>
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


    <!--<script src="app.js"></script>-->
    <!--<script src="efectos.js"></script>-->

</body>
</html>