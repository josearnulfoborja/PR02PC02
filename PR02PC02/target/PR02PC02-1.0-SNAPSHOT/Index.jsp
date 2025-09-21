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

  <script src="registro.js"></script>
</body>
</html>