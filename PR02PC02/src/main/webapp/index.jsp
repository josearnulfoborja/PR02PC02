document.getElementById('btnRegistrar').onclick = () => {
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
<h2>Registro de Usuario</h2>
<form id="formRegistro" autocomplete="off">
    <label>Nombre: <input type="text" id="nombre" required></label><br>
    <label>Apellido: <input type="text" id="apellido" required></label><br>
    <label>Correo electrónico: <input type="email" id="correo" required></label><br>
    <label>Teléfono: <input type="text" id="telefono" required></label><br>
    <label>Contraseña: <input type="password" id="clave" required></label><br>
    <label>Confirmar contraseña: <input type="password" id="clave2" required></label><br>
    <div id="captchaTexto"></div>
    <label>Escribe el texto del captcha: <input type="text" id="captchaInput" required></label><br>
    <button type="button" id="btnRegistrar">REGISTRARSE</button>
</form>
<div id="resultado"></div>
<script src="app.js"></script>
</body>
</html>
