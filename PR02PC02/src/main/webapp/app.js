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
    event.preventDefault();
    let valido = true;
    let campos = ["nombre", "apellido", "correo", "telefono", "password", "confirmPassword", "captchaRespuesta"];
    campos.forEach(id => {
      let campo = document.getElementById(id);
      campo.style.borderColor = ""; // Limpia color previo
      if (!campo.value.trim()) {
        campo.style.borderColor = "red";
        valido = false;
        document.getElementById("resultado").innerText = "Todos los campos son obligatorios";
      }
    });
    if (!valido) return;

    const usuario = {
      nombre: capitalizarCadaPalabra(document.getElementById("nombre").value),
      apellido: capitalizarCadaPalabra(document.getElementById("apellido").value),
      clave: document.getElementById("password").value,
      correo: document.getElementById("correo").value,
      telefono: document.getElementById("telefono").value,
      roll: "usuario",
      estado: "Inactivo",
      codigoVerificacion: "",
      modoAutenticacion: "Autoregistro",
      direccionIP: "" // Se obtiene en backend
    };

    fetch("/PR02PC02/SrvUsuario", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(usuario)
    })
    .then(res => {
      if (!res.ok) {
        throw new Error("Error en la respuesta del servidor");
      }
      return res.json();
    })
    .then(data => {
      document.getElementById("resultado").innerText = data.mensaje;
    })
    .catch(err => {
      console.error("Error de conexión:", err);
      document.getElementById("resultado").innerText = "Error de conexión con el servidor";
    });
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