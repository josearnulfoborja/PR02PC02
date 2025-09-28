<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro de Usuario</title>
  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="estilos.css">

</head>
<body>

  <form id="formRegistro">
    <h2>Registro de Usuario</h2>
    <input type="text" id="nombre" placeholder="Nombre" required>
    <input type="text" id="apellido" placeholder="Apellido" required>
    <input type="email" id="correo" placeholder="Correo electrónico" required>
    <input type="text" id="telefono" placeholder="Teléfono" required>
    <input type="password" id="password" placeholder="Contraseña" required>
    <input type="password" id="confirmPassword" placeholder="Confirmar contraseña" required>
    <div id="captchaContainer"></div>
    <input type="text" id="captchaRespuesta" placeholder="Resuelve el captcha" required>
       <button type="button" id="registrarse">Registrarse</button>
  </form>
  <div id="pantallaPractica" style="display:none;"></div>
  <script src="app.js"></script>
</body>
</html>