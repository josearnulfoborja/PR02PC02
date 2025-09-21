<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro de Usuario</title>
  </head>
<body>

  <form id="formRegistro">
    <h2>Registro de Usuario</h2>
    <input type="text" id="nombre" placeholder="Nombre" required>
    <input type="text" id="apellido" placeholder="Apellido" required>
    <input type="email" id="correo" placeholder="Correo electrónico" required>
    <input type="text" id="telefono" placeholder="Teléfono" required>
    <input type="password" id="password" placeholder="Contraseña" required>
    <button type="button" id="registrarse">Registrarse</button>
  </form>

  <script>
    // Clase Usuario
    class Usuario {
      constructor(nombre, apellido, correo, telefono, password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
      }
    }

    // Validaciones
    const capitalizarTexto = texto =>
      texto.trim().charAt(0).toUpperCase() + texto.trim().slice(1).toLowerCase();

    const limpiarEspacios = texto => texto.replace(/\s+/g, "");

    const validarCorreo = correo => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correo);

    const validarTelefono = telefono => /^[0-9]{8,15}$/.test(telefono);

    const validarPassword = password =>
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password);

    // Evento en botón REGISTRARSE con arrow function
    document.getElementById("registrarse").addEventListener("click", () => {
      let nombre = capitalizarTexto(document.getElementById("nombre").value);
      let apellido = capitalizarTexto(document.getElementById("apellido").value);
      let correo = document.getElementById("correo").value;
      let telefono = limpiarEspacios(document.getElementById("telefono").value);
      let password = document.getElementById("password").value;

      // Validaciones
      if (!validarCorreo(correo)) return alert("❌ Correo inválido");
      if (!validarTelefono(telefono)) return alert("❌ Teléfono inválido (8-15 dígitos)");
      if (!validarPassword(password)) return alert("❌ La contraseña debe tener mínimo 8 caracteres, mayúscula, minúscula, número y símbolo");

      // Crear usuario
      const usuario = new Usuario(nombre, apellido, correo, telefono, password);
      enviarDatos(usuario);
    });

    // Envío con Fetch API
    function enviarDatos(usuario) {
      fetch("https://miapi.com/registro", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      })
      .then(res => res.json())
      .then(data => {
        console.log("Registro exitoso:", data);
        alert("✅ Usuario registrado con éxito");
        document.getElementById("formRegistro").reset();
      })
      .catch(err => console.error("Error en el registro:", err));
    }
  </script>

</body>
</html>