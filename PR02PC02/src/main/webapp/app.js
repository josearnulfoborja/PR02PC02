document.addEventListener("DOMContentLoaded", () => {
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

  function capitalizarCadaPalabra(texto) {
    return texto
      .trim()
      .split(/\s+/)
      .map(palabra => palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase())
      .join(' ');
  }

  const limpiarEspacios = texto => texto.replace(/\s+/g, "");

  const validarCorreo = correo => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correo);

  const validarTelefono = telefono => /^[0-9]{8,15}$/.test(telefono);

  const validarPassword = password =>
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(password);

  const empiezaConMayuscula = texto => /^[A-ZÁÉÍÓÚÑ]/.test(texto.trim());

  const campoNoVacio = texto => texto.trim().length > 0;

  // Captcha simple
  let captchaA = Math.floor(Math.random() * 10) + 1;
  let captchaB = Math.floor(Math.random() * 10) + 1;
  let captchaResultado = captchaA + captchaB;
  document.getElementById("captchaContainer").innerHTML =
    `<label for="captchaRespuesta">¿Cuánto es ${captchaA} + ${captchaB}?</label>`;

  // Evento en botón REGISTRARSE con arrow function
  document.getElementById("registrarse").addEventListener("click", (event) => {
    event.preventDefault(); // Evita el submit y recarga
    let nombre = capitalizarCadaPalabra(document.getElementById("nombre").value);
    let apellido = capitalizarCadaPalabra(document.getElementById("apellido").value);
    let correo = document.getElementById("correo").value;
    let telefono = limpiarEspacios(document.getElementById("telefono").value);
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let captchaRespuesta = document.getElementById("captchaRespuesta").value;

    // Validaciones
    if (!campoNoVacio(nombre)) return alert("❌ El nombre no puede estar vacío ni contener solo espacios");
    if (!campoNoVacio(apellido)) return alert("❌ El apellido no puede estar vacío ni contener solo espacios");
    if (!empiezaConMayuscula(nombre)) return alert("❌ El nombre debe empezar con mayúscula");
    if (!empiezaConMayuscula(apellido)) return alert("❌ El apellido debe empezar con mayúscula");
    if (!validarCorreo(correo)) return alert("❌ Correo inválido");
    if (!validarTelefono(telefono)) return alert("❌ Teléfono inválido (8-15 dígitos)");
    if (!validarPassword(password)) return alert("❌ La contraseña debe tener mínimo 8 caracteres, mayúscula, minúscula, número y símbolo");
    if (password !== confirmPassword) return alert("❌ Las contraseñas no coinciden");
    if (parseInt(captchaRespuesta) !== captchaResultado) return alert("❌ Captcha incorrecto");

    // Crear usuario
    const usuario = new Usuario(
      nombre,
      apellido,
      correo,
      telefono,
      password
    );
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
      // Genera nuevo captcha tras registro
      window.location.href = "pag2.jsp";
      captchaA = Math.floor(Math.random() * 10) + 1;
      captchaB = Math.floor(Math.random() * 10) + 1;
      captchaResultado = captchaA + captchaB;
      document.getElementById("captchaContainer").innerHTML =
        `<label for="captchaRespuesta">¿Cuánto es ${captchaA} + ${captchaB}?</label>`;
    })
    .catch(err => console.error("Error en el registro:", err));
  }
});