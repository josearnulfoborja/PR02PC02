// Captcha simple
document.addEventListener('DOMContentLoaded', () => {
    const captcha = Math.random().toString(36).substring(2, 8).toUpperCase();
    document.getElementById('captchaTexto').innerText = captcha;

    class Usuario {
        constructor(nombre, apellido, correo, telefono, clave) {
            this.nombre = nombre.trim().toUpperCase();
            this.apellido = apellido.trim().toUpperCase();
            this.correo = correo.trim();
            this.telefono = telefono.trim();
            this.clave = clave;
            this.mensaje = "";
            this.estado = "";
        }
        static validarNombre(nombre) {
            return /^[A-ZÁÉÍÓÚÑ ]+$/.test(nombre.trim().toUpperCase());
        }
        static validarApellido(apellido) {
            return /^[A-ZÁÉÍÓÚÑ ]+$/.test(apellido.trim().toUpperCase());
        }
        static validarCorreo(correo) {
            return /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(correo.trim());
        }
        static validarTelefono(telefono) {
            return /^[0-9]{7,15}$/.test(telefono.trim());
        }
        static validarClave(clave, clave2) {
            return clave === clave2 && clave.length >= 6;
        }
    }

    const form = document.getElementById('formRegistro');
    const resultado = document.getElementById('resultado');

    document.getElementById('btnRegistrar').onclick = () => {
        // Limpiar estilos
        Array.from(form.elements).forEach(e => e.classList.remove('error', 'ok'));
        resultado.innerText = '';
        // Obtener valores
        const nombre = document.getElementById('nombre').value;
        const apellido = document.getElementById('apellido').value;
        const correo = document.getElementById('correo').value;
        const telefono = document.getElementById('telefono').value;
        const clave = document.getElementById('clave').value;
        const clave2 = document.getElementById('clave2').value;
        const captchaInput = document.getElementById('captchaInput').value.trim().toUpperCase();
        let valido = true;
        // Validaciones
        if (!Usuario.validarNombre(nombre)) {
            document.getElementById('nombre').classList.add('error');
            valido = false;
        }
        if (!Usuario.validarApellido(apellido)) {
            document.getElementById('apellido').classList.add('error');
            valido = false;
        }
        if (!Usuario.validarCorreo(correo)) {
            document.getElementById('correo').classList.add('error');
            valido = false;
        }
        if (!Usuario.validarTelefono(telefono)) {
            document.getElementById('telefono').classList.add('error');
            valido = false;
        }
        if (!Usuario.validarClave(clave, clave2)) {
            document.getElementById('clave').classList.add('error');
            document.getElementById('clave2').classList.add('error');
            valido = false;
        }
        if (captchaInput !== captcha) {
            document.getElementById('captchaInput').classList.add('error');
            valido = false;
        }
        if (!valido) {
            resultado.innerText = 'Por favor, corrige los campos marcados.';
            return;
        }
        // Crear objeto Usuario
        const usuario = new Usuario(nombre, apellido, correo, telefono, clave);
        // Enviar por Fetch
        fetch('SrvUsuario', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuario)
        })
        .then(r => r.json())
        .then(data => {
            resultado.innerText = data.mensaje;
            if (data.estado === 'OK') {
                form.reset();
            }
        })
        .catch(() => {
            resultado.innerText = 'Error de comunicación con el servidor.';
        });
    };
});
