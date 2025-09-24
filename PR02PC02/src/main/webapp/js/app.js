/**
 * OBJETO USUARIO - VALIDACIÓN DE FORMULARIO DE REGISTRO
 * Archivo: validacion.js
 * Descripción: Maneja la validación completa del formulario de registro
 */

// Objeto Usuario para manejar validaciones
const Usuario = {
    // Propiedades del usuario
    nombre: '',
    apellido: '',
    correo: '',
    telefono: '',
    clave: '',
    mensaje: '',
    estado: false,

    // Método para inicializar el objeto con datos del formulario
    inicializar: function(datos) {
        this.nombre = datos.nombre || '';
        this.apellido = datos.apellido || '';
        this.correo = datos.correo || '';
        this.telefono = datos.telefono || '';
        this.clave = datos.clave || '';
        this.mensaje = '';
        this.estado = false;
    },

    // Método para limpiar espacios al inicio y final
    limpiarEspacios: function() {
        this.nombre = this.nombre.trim();
        this.apellido = this.apellido.trim();
        this.correo = this.correo.trim();
        this.telefono = this.telefono.trim();
        this.clave = this.clave.trim();
    },

    // Método para formatear nombres con primera letra en mayúscula para cada palabra
    formatearNombres: function() {
        // Función auxiliar para capitalizar cada palabra
        const capitalizarCadaPalabra = (texto) => {
            if (!texto || texto.length === 0) return texto;
            
            return texto.trim()
                       .split(/\s+/) // Dividir por uno o más espacios
                       .map(palabra => {
                           if (palabra.length === 0) return palabra;
                           return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
                       })
                       .join(' '); // Unir con un solo espacio
        };
        
        this.nombre = capitalizarCadaPalabra(this.nombre);
        this.apellido = capitalizarCadaPalabra(this.apellido);
    },

    // Método para validar que nombres y apellidos no contengan números
    validarNombresApellidos: function() {
        const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
        const errores = [];

        if (!regex.test(this.nombre)) {
            errores.push({
                campo: 'nombre',
                mensaje: 'El nombre no debe contener números ni caracteres especiales'
            });
        }

        if (!regex.test(this.apellido)) {
            errores.push({
                campo: 'apellido',
                mensaje: 'El apellido no debe contener números ni caracteres especiales'
            });
        }

        return errores;
    },

    // Método para validar formato de correo electrónico
    validarCorreo: function() {
        const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        
        if (!regex.test(this.correo)) {
            return {
                campo: 'correo',
                mensaje: 'El formato del correo electrónico no es válido'
            };
        }
        return null;
    },

    // Método para validar formato de teléfono
    validarTelefono: function() {
        // Acepta formatos: +### ####### o #######
        // Debe tener exactamente 8 números (sin contar el código de país)
        const regex = /^(\+\d{3}\s\d{8}|\d{8})$/;
        
        if (!regex.test(this.telefono)) {
            return {
                campo: 'telefono',
                mensaje: 'El formato del teléfono no es válido (ej. +503 12345678 o 12345678 (8 números sin guiones))'
            };
        }
        return null;
    },

    // Método para validar que las contraseñas coincidan
    validarContrasenas: function(confirmarClave) {
        if (this.clave !== confirmarClave) {
            return {
                campo: 'confirmarClave',
                mensaje: 'Las contraseñas no coinciden'
            };
        }

        // Validar fortaleza de la contraseña
        const errorClave = this.validarFortalezaClave();
        if (errorClave) {
            return errorClave;
        }

        return null;
    },

    // Método para validar fortaleza de la contraseña
    validarFortalezaClave: function() {
        // Mínimo 6 caracteres
        if (this.clave.length < 6) {
            return {
                campo: 'clave',
                mensaje: 'La contraseña debe tener mínimo 6 caracteres'
            };
        }

        // Debe contener al menos una letra
        if (!/[a-zA-Z]/.test(this.clave)) {
            return {
                campo: 'clave',
                mensaje: 'La contraseña debe contener al menos una letra'
            };
        }

        // Debe contener al menos un número
        if (!/[0-9]/.test(this.clave)) {
            return {
                campo: 'clave',
                mensaje: 'La contraseña debe contener al menos un número'
            };
        }

        // Debe contener al menos una mayúscula
        if (!/[A-Z]/.test(this.clave)) {
            return {
                campo: 'clave',
                mensaje: 'La contraseña debe contener al menos una letra mayúscula'
            };
        }

        // Debe contener al menos una minúscula
        if (!/[a-z]/.test(this.clave)) {
            return {
                campo: 'clave',
                mensaje: 'La contraseña debe contener al menos una letra minúscula'
            };
        }

        return null;
    },

    // Método para validar si el correo ya existe (async)
    validarCorreoDuplicado: async function() {
        if (!this.correo || this.correo.trim() === '') {
            return null; // No validar si está vacío
        }

        try {
            // Crear un endpoint específico para validar correo
            const response = await fetch('SrvUsuario?action=verificarCorreo', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    correo: this.correo
                })
            });

            const resultado = await response.json();
            
            if (resultado.existe) {
                return {
                    campo: 'correo',
                    mensaje: 'Este correo electrónico ya está registrado'
                };
            }

            return null;
        } catch (error) {
            console.error('Error al validar correo duplicado:', error);
            // En caso de error, no bloquear el registro
            return null;
        }
    },

    // Método para validar campos obligatorios
    validarCamposObligatorios: function() {
        const errores = [];
        
        if (!this.nombre || this.nombre.length === 0) {
            errores.push({
                campo: 'nombre',
                mensaje: 'El nombre es obligatorio'
            });
        }

        if (!this.apellido || this.apellido.length === 0) {
            errores.push({
                campo: 'apellido',
                mensaje: 'El apellido es obligatorio'
            });
        }

        if (!this.correo || this.correo.length === 0) {
            errores.push({
                campo: 'correo',
                mensaje: 'El correo electrónico es obligatorio'
            });
        }

        if (!this.telefono || this.telefono.length === 0) {
            errores.push({
                campo: 'telefono',
                mensaje: 'El teléfono es obligatorio'
            });
        }

        if (!this.clave || this.clave.length === 0) {
            errores.push({
                campo: 'clave',
                mensaje: 'La contraseña es obligatoria'
            });
        }

        return errores;
    },

    // Método principal de validación
    validar: function(confirmarClave) {
        let errores = [];

        // 1. Limpiar espacios
        this.limpiarEspacios();

        // 2. Validar campos obligatorios
        errores = errores.concat(this.validarCamposObligatorios());

        // 3. Si hay campos obligatorios faltantes, no continuar
        if (errores.length > 0) {
            this.mensaje = 'Por favor complete todos los campos obligatorios';
            this.estado = false;
            return { valido: false, errores: errores };
        }

        // 4. Formatear nombres con primera letra en mayúscula
        this.formatearNombres();

        // 5. Validar nombres y apellidos
        errores = errores.concat(this.validarNombresApellidos());

        // 6. Validar correo
        const errorCorreo = this.validarCorreo();
        if (errorCorreo) errores.push(errorCorreo);

        // 7. Validar teléfono
        const errorTelefono = this.validarTelefono();
        if (errorTelefono) errores.push(errorTelefono);

        // 8. Validar contraseñas
        const errorClave = this.validarContrasenas(confirmarClave);
        if (errorClave) errores.push(errorClave);

        // 9. Establecer resultado
        if (errores.length === 0) {
            this.mensaje = 'Usuario válido - Datos procesados correctamente';
            this.estado = true;
            return { valido: true, errores: [] };
        } else {
            this.mensaje = 'Por favor corrija los errores señalados';
            this.estado = false;
            return { valido: false, errores: errores };
        }
    },

    // Método para obtener información del usuario
    obtenerInfo: function() {
        return {
            nombre: this.nombre,
            apellido: this.apellido,
            correo: this.correo,
            telefono: this.telefono,
            mensaje: this.mensaje,
            estado: this.estado
        };
    }
};

// Objeto CAPTCHA para generación y validación
const CAPTCHA = {
    codigoActual: '',
    
    // Generar código CAPTCHA aleatorio con mayúsculas, minúsculas y números
    generarCodigo: function() {
        const mayusculas = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        const minusculas = 'abcdefghijklmnopqrstuvwxyz';
        const numeros = '0123456789';
        const todosCaracteres = mayusculas + minusculas + numeros;
        
        let codigo = '';
        
        // Asegurar que tenga al menos una mayúscula, una minúscula y un número
        codigo += mayusculas.charAt(Math.floor(Math.random() * mayusculas.length));
        codigo += minusculas.charAt(Math.floor(Math.random() * minusculas.length));
        codigo += numeros.charAt(Math.floor(Math.random() * numeros.length));
        
        // Completar con 3 caracteres aleatorios más
        for (let i = 3; i < 6; i++) {
            codigo += todosCaracteres.charAt(Math.floor(Math.random() * todosCaracteres.length));
        }
        
        // Mezclar el código para que no siga un patrón predecible
        codigo = codigo.split('').sort(() => Math.random() - 0.5).join('');
        
        this.codigoActual = codigo;
        return codigo;
    },
    
    // Mostrar el código en el elemento HTML
    mostrarCodigo: function() {
        const elementoCaptcha = document.querySelector('.captcha-text');
        if (elementoCaptcha) {
            elementoCaptcha.textContent = this.codigoActual;
        }
    },
    
    // Inicializar CAPTCHA
    inicializar: function() {
        this.generarCodigo();
        this.mostrarCodigo();
        
        // Agregar evento click para regenerar CAPTCHA
        const elementoCaptcha = document.querySelector('.captcha-text');
        if (elementoCaptcha) {
            elementoCaptcha.style.cursor = 'pointer';
            elementoCaptcha.title = 'Clic para cambiar el código - Respete mayúsculas y minúsculas';
            elementoCaptcha.addEventListener('click', () => {
                this.regenerar();
            });
        }
        
        // Agregar información al campo de entrada
        const inputCaptcha = document.querySelector('.captcha input');
        if (inputCaptcha) {
            inputCaptcha.title = 'Escriba el código exactamente como se muestra (mayúsculas y minúsculas)';
        }
    },
    
    // Regenerar código CAPTCHA
    regenerar: function() {
        this.generarCodigo();
        this.mostrarCodigo();
        
        // Limpiar el campo de entrada
        const inputCaptcha = document.querySelector('.captcha input');
        if (inputCaptcha) {
            inputCaptcha.value = '';
            inputCaptcha.style.borderColor = '#444';
            inputCaptcha.parentElement.classList.remove('error');
        }
    },
    
    // Validar si el código ingresado es correcto (sensible a mayúsculas/minúsculas)
    validar: function(codigoIngresado) {
        return codigoIngresado === this.codigoActual;
    },
    
    // Obtener el valor del campo de entrada
    obtenerValorIngresado: function() {
        const inputCaptcha = document.querySelector('.captcha input');
        return inputCaptcha ? inputCaptcha.value.trim() : '';
    }
};

// Funciones auxiliares para manejo del DOM
const ValidacionUI = {
    
    // Marcar campo como inválido (en rojo)
    marcarError: function(nombreCampo, mensaje) {
        const campo = document.querySelector(`input[placeholder*="${this.obtenerPlaceholder(nombreCampo)}"]`) || 
                     document.querySelector(`input[name="${nombreCampo}"]`);
        
        if (campo) {
            // Agregar clase de error
            campo.parentElement.classList.add('error');
            campo.style.borderColor = '#f56565';
            campo.style.backgroundColor = '#fed7d7';
            
            // Mostrar mensaje de error
            this.mostrarMensajeError(campo, mensaje);
        }
    },

    // Marcar campo como válido (verde)
    marcarValido: function(nombreCampo) {
        const campo = document.querySelector(`input[placeholder*="${this.obtenerPlaceholder(nombreCampo)}"]`) || 
                     document.querySelector(`input[name="${nombreCampo}"]`);
        
        if (campo) {
            // Remover clase de error
            campo.parentElement.classList.remove('error');
            campo.style.borderColor = '#34b68d';
            campo.style.backgroundColor = '#f0fff4';
            
            // Remover mensaje de error
            this.removerMensajeError(campo);
        }
    },

    // Limpiar todos los estilos de error
    limpiarEstilos: function() {
        const campos = document.querySelectorAll('.input-group');
        campos.forEach(grupo => {
            grupo.classList.remove('error');
            const input = grupo.querySelector('input');
            if (input) {
                input.style.borderColor = '';
                input.style.backgroundColor = '';
            }
            this.removerMensajeError(input);
        });
    },

    // Mostrar mensaje de error debajo del campo
    mostrarMensajeError: function(campo, mensaje) {
        this.removerMensajeError(campo);
        
        const mensajeDiv = document.createElement('div');
        mensajeDiv.className = 'mensaje-error';
        mensajeDiv.textContent = mensaje;
        
        // Insertar el mensaje dentro del input-group, al final
        const inputGroup = campo.parentElement;
        inputGroup.appendChild(mensajeDiv);
    },

    // Remover mensaje de error
    removerMensajeError: function(campo) {
        if (campo && campo.parentElement) {
            const inputGroup = campo.parentElement;
            const mensajeExistente = inputGroup.querySelector('.mensaje-error');
            if (mensajeExistente) {
                mensajeExistente.remove();
            }
        }
    },

    // Obtener placeholder según el nombre del campo
    obtenerPlaceholder: function(nombreCampo) {
        const placeholders = {
            'nombre': 'Nombre',
            'apellido': 'Apellido',
            'correo': 'Correo',
            'telefono': 'Teléfono',
            'clave': 'Contraseña',
            'confirmarClave': 'Confirmar'
        };
        return placeholders[nombreCampo] || nombreCampo;
    },

    // Mostrar resultado general
    mostrarResultado: function(mensaje, esError = false) {
        const resultado = document.getElementById('resultado');
        if (resultado) {
            resultado.textContent = mensaje;
            resultado.className = esError ? 'error' : 'success';
            resultado.style.display = 'block';
        }
    }
};

// Función asíncrona para validar el formulario incluyendo verificación de correo duplicado
async function validarFormularioCompleto() {
    // Obtener valores del formulario
    const datos = {
        nombre: document.querySelector('input[placeholder="Nombre"]')?.value || '',
        apellido: document.querySelector('input[placeholder="Apellido"]')?.value || '',
        correo: document.querySelector('input[placeholder="Correo electrónico"]')?.value || '',
        telefono: document.querySelector('input[placeholder="Teléfono"]')?.value || '',
        clave: document.querySelector('input[placeholder="Contraseña"]')?.value || ''
    };

    const confirmarClave = document.querySelector('input[placeholder="Confirmar contraseña"]')?.value || '';

    // Limpiar estilos anteriores
    ValidacionUI.limpiarEstilos();

    // Inicializar y validar usuario
    Usuario.inicializar(datos);
    const resultado = Usuario.validar(confirmarClave);

    // Si hay errores básicos, no continuar
    if (!resultado.valido) {
        resultado.errores.forEach(error => {
            ValidacionUI.marcarError(error.campo, error.mensaje);
        });
        ValidacionUI.mostrarResultado(Usuario.mensaje, true);
        return false;
    }

    // Validar correo duplicado
    try {
        ValidacionUI.mostrarResultado('Verificando disponibilidad del correo...', false);
        const errorCorreoDuplicado = await Usuario.validarCorreoDuplicado();
        
        if (errorCorreoDuplicado) {
            ValidacionUI.marcarError(errorCorreoDuplicado.campo, errorCorreoDuplicado.mensaje);
            ValidacionUI.mostrarResultado(errorCorreoDuplicado.mensaje, true);
            return false;
        }
    } catch (error) {
        console.error('Error al validar correo duplicado:', error);
        // Continuar sin bloquear en caso de error de conectividad
    }

    // Validar CAPTCHA
    const codigoCaptcha = CAPTCHA.obtenerValorIngresado();
    const captchaValido = CAPTCHA.validar(codigoCaptcha);
    
    if (!captchaValido) {
        // Marcar CAPTCHA como inválido
        const inputCaptcha = document.querySelector('.captcha input');
        if (inputCaptcha) {
            inputCaptcha.style.borderColor = '#f56565';
            inputCaptcha.parentElement.classList.add('error');
        }
        
        ValidacionUI.mostrarResultado('El código CAPTCHA es incorrecto. Debe coincidir exactamente (mayúsculas y minúsculas). Haga clic en el código para cambiarlo.', true);
        return false;
    }

    // Marcar todos los campos como válidos
    Object.keys(datos).forEach(campo => {
        ValidacionUI.marcarValido(campo);
    });
    ValidacionUI.marcarValido('confirmarClave');
    
    // Marcar CAPTCHA como válido
    const inputCaptcha = document.querySelector('.captcha input');
    if (inputCaptcha) {
        inputCaptcha.style.borderColor = '#34b68d';
        inputCaptcha.parentElement.classList.remove('error');
    }
    
    ValidacionUI.mostrarResultado('Validación exitosa', false);
    
    console.log('Usuario validado:', Usuario.obtenerInfo());
    return true;
}

// Función principal para validar el formulario
function validarFormulario() {
    // Obtener valores del formulario
    const datos = {
        nombre: document.querySelector('input[placeholder="Nombre"]')?.value || '',
        apellido: document.querySelector('input[placeholder="Apellido"]')?.value || '',
        correo: document.querySelector('input[placeholder="Correo electrónico"]')?.value || '',
        telefono: document.querySelector('input[placeholder="Teléfono"]')?.value || '',
        clave: document.querySelector('input[placeholder="Contraseña"]')?.value || ''
    };

    const confirmarClave = document.querySelector('input[placeholder="Confirmar contraseña"]')?.value || '';

    // Limpiar estilos anteriores
    ValidacionUI.limpiarEstilos();

    // Inicializar y validar usuario
    Usuario.inicializar(datos);
    const resultado = Usuario.validar(confirmarClave);
    
    // Validar CAPTCHA
    const codigoCaptcha = CAPTCHA.obtenerValorIngresado();
    const captchaValido = CAPTCHA.validar(codigoCaptcha);
    
    if (!captchaValido) {
        // Marcar CAPTCHA como inválido
        const inputCaptcha = document.querySelector('.captcha input');
        if (inputCaptcha) {
            inputCaptcha.style.borderColor = '#f56565';
            inputCaptcha.parentElement.classList.add('error');
        }
        
        ValidacionUI.mostrarResultado('El código CAPTCHA es incorrecto. Debe coincidir exactamente (mayúsculas y minúsculas). Haga clic en el código para cambiarlo.', true);
        return false;
    }

    // Mostrar errores en la UI
    if (!resultado.valido) {
        resultado.errores.forEach(error => {
            ValidacionUI.marcarError(error.campo, error.mensaje);
        });
        ValidacionUI.mostrarResultado(Usuario.mensaje, true);
        return false;
    } else {
        // Marcar todos los campos como válidos
        Object.keys(datos).forEach(campo => {
            ValidacionUI.marcarValido(campo);
        });
        ValidacionUI.marcarValido('confirmarClave');
        
        // Marcar CAPTCHA como válido
        const inputCaptcha = document.querySelector('.captcha input');
        if (inputCaptcha) {
            inputCaptcha.style.borderColor = '#34b68d';
            inputCaptcha.parentElement.classList.remove('error');
        }
        
        ValidacionUI.mostrarResultado(Usuario.mensaje, false);
        
        console.log('Usuario validado:', Usuario.obtenerInfo());
        return true;
    }
}

// Función para enviar datos al servlet mediante Fetch API
async function enviarDatosUsuario() {
    try {
        // Convertir el objeto Usuario a JSON
        const usuarioJSON = {
            nombre: Usuario.nombre,
            apellido: Usuario.apellido,
            correo: Usuario.correo,
            telefono: Usuario.telefono,
            clave: Usuario.clave
        };

        console.log('Enviando datos:', usuarioJSON);
        
        // Mostrar estado de carga
        ValidacionUI.mostrarResultado('Enviando datos...', false);
        document.querySelector('form').classList.add('loading');

        // Enviar datos con Fetch API
        const response = await fetch('SrvUsuario', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(usuarioJSON)
        });

        // Verificar respuesta
        console.log('Status de respuesta:', response.status);
        
        if (response.ok) {
            const resultado = await response.json();
            console.log('Respuesta del servidor:', resultado);
            
            // Verificar el estado en la respuesta JSON
            if (resultado.estado === 'OK') {
                // Mostrar mensaje de éxito del servidor
                ValidacionUI.mostrarResultado(resultado.mensaje, false);
                
                // Opcional: Limpiar formulario después del éxito
                setTimeout(() => {
                    limpiarFormulario();
                }, 3000);
            } else {
                // Error reportado por el servidor
                ValidacionUI.mostrarResultado(resultado.mensaje || 'Error en el registro', true);
            }
            
        } else {
            // Error HTTP - manejar diferentes tipos de error
            console.log('Error HTTP - Status:', response.status);
            
            try {
                const errorData = await response.json();
                console.log('Datos de error del servidor:', errorData);
                
                // Manejar específicamente error de correo duplicado (status 409)
                if (response.status === 409) {
                    ValidacionUI.mostrarResultado(errorData.mensaje || 'El correo electrónico ya está registrado', true);
                    // Marcar el campo de correo como error
                    ValidacionUI.marcarError('correo', 'Este correo ya está registrado');
                } else if (response.status === 400) {
                    // Error de validación (Bad Request)
                    ValidacionUI.mostrarResultado(errorData.mensaje || 'Datos inválidos. Verifique la información ingresada.', true);
                } else {
                    // Otros errores del servidor
                    const mensajeError = errorData.mensaje || 'Error al registrar usuario. Intente nuevamente.';
                    ValidacionUI.mostrarResultado(mensajeError, true);
                }
                
            } catch (e) {
                // Si no se puede parsear como JSON, usar mensaje basado en status
                let mensajeGenerico = 'Error al registrar usuario. Intente nuevamente.';
                
                if (response.status === 409) {
                    mensajeGenerico = 'El correo electrónico ya está registrado.';
                    ValidacionUI.marcarError('correo', 'Este correo ya está registrado');
                } else if (response.status === 400) {
                    mensajeGenerico = 'Datos inválidos. Verifique la información ingresada.';
                } else if (response.status >= 500) {
                    mensajeGenerico = 'Error interno del servidor. Intente más tarde.';
                }
                
                ValidacionUI.mostrarResultado(mensajeGenerico, true);
            }
        }

    } catch (error) {
        // Error de red o conexión
        console.error('Error de conexión:', error);
        ValidacionUI.mostrarResultado('Error de conexión. Verifique su internet.', true);
    } finally {
        // Remover estado de carga
        document.querySelector('form').classList.remove('loading');
    }
}

// Función para limpiar el formulario
function limpiarFormulario() {
    const campos = document.querySelectorAll('.input-group input');
    campos.forEach(campo => {
        campo.value = '';
        ValidacionUI.marcarValido(campo.placeholder);
    });
    
    // Limpiar campo CAPTCHA
    const inputCaptcha = document.querySelector('.captcha input');
    if (inputCaptcha) {
        inputCaptcha.value = '';
        inputCaptcha.style.borderColor = '#444';
        inputCaptcha.parentElement.classList.remove('error');
    }
    
    // Regenerar CAPTCHA
    CAPTCHA.regenerar();
    
    // Limpiar objeto Usuario
    Usuario.inicializar({});
    
    // Limpiar resultado
    const resultado = document.getElementById('resultado');
    if (resultado) {
        resultado.textContent = '';
        resultado.className = '';
        resultado.style.display = 'none';
    }
}

// Event listeners para validación en tiempo real
document.addEventListener('DOMContentLoaded', function() {
    // Validación en tiempo real para cada campo
    const campos = document.querySelectorAll('.input-group input');
    
    campos.forEach(campo => {
        campo.addEventListener('blur', function() {
            // Validar solo este campo específico
            validarCampoIndividual(this);
        });

        campo.addEventListener('input', function() {
            // Limpiar error si el usuario está escribiendo
            if (this.parentElement.classList.contains('error')) {
                ValidacionUI.marcarValido(this.placeholder);
            }
        });
    });

    // Agregar event listener al formulario
    const formulario = document.getElementById('registroForm');
    if (formulario) {
        formulario.addEventListener('submit', async function(e) {
            e.preventDefault();
            const validacionExitosa = await validarFormularioCompleto();
            if (validacionExitosa) {
                // Si la validación es exitosa, enviar datos al servlet
                enviarDatosUsuario();
            }
        });
    }
    
    // Inicializar CAPTCHA al cargar la página
    CAPTCHA.inicializar();
    
    // Agregar validación en tiempo real al campo CAPTCHA
    const inputCaptcha = document.querySelector('.captcha input');
    if (inputCaptcha) {
        inputCaptcha.addEventListener('input', function() {
            if (this.parentElement.classList.contains('error')) {
                this.style.borderColor = '#444';
                this.parentElement.classList.remove('error');
            }
        });
    }
});

// Función para validar campo individual
function validarCampoIndividual(campo) {
    const placeholder = campo.placeholder.toLowerCase();
    const valor = campo.value.trim();

    if (!valor) return; // No validar campos vacíos en blur

    // Crear objeto temporal para validación individual
    const usuarioTemp = Object.create(Usuario);
    
    if (placeholder.includes('nombre')) {
        usuarioTemp.nombre = valor;
        const errores = usuarioTemp.validarNombresApellidos();
        const error = errores.find(e => e.campo === 'nombre');
        if (error) {
            ValidacionUI.marcarError('nombre', error.mensaje);
        } else {
            ValidacionUI.marcarValido('nombre');
        }
    } else if (placeholder.includes('apellido')) {
        usuarioTemp.apellido = valor;
        const errores = usuarioTemp.validarNombresApellidos();
        const error = errores.find(e => e.campo === 'apellido');
        if (error) {
            ValidacionUI.marcarError('apellido', error.mensaje);
        } else {
            ValidacionUI.marcarValido('apellido');
        }
    } else if (placeholder.includes('correo')) {
        usuarioTemp.correo = valor;
        const error = usuarioTemp.validarCorreo();
        if (error) {
            ValidacionUI.marcarError('correo', error.mensaje);
        } else {
            ValidacionUI.marcarValido('correo');
        }
    } else if (placeholder.includes('teléfono')) {
        usuarioTemp.telefono = valor;
        const error = usuarioTemp.validarTelefono();
        if (error) {
            ValidacionUI.marcarError('telefono', error.mensaje);
        } else {
            ValidacionUI.marcarValido('telefono');
        }
    } else if (placeholder.includes('contraseña') && !placeholder.includes('confirmar')) {
        usuarioTemp.clave = valor;
        const error = usuarioTemp.validarFortalezaClave();
        if (error) {
            ValidacionUI.marcarError('clave', error.mensaje);
        } else {
            ValidacionUI.marcarValido('clave');
        }
    }
}