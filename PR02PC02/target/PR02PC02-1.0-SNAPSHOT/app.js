document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const resultado = document.getElementById('resultado');
    if (!form) return;

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        // Obtener los campos del formulario
        const inputs = form.querySelectorAll('input');
        const data = {
            nombre: inputs[0].value,
            apellido: inputs[1].value,
            correo: inputs[2].value,
            telefono: inputs[3].value,
            password: inputs[4].value
        };
         //let Url='http://localhost:8080/PR02PC02/SrvUsuario';
        fetch('resources/usuarios/registro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(json => {
            resultado.textContent = json.mensaje || 'Registro exitoso';
        })
        .catch(err => {
            resultado.textContent = 'Error en el registro';
        });
    });
});