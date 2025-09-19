<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Página JSP con Botones</title>
    <style>
        #miDiv {
            width: 300px;
            height: 100px;
            background-color: #f0f0f0;
            margin: 20px 0;
            display: none;
            padding: 20px;
        }
    </style>
    <script>
        function mostrarDiv() {
            document.getElementById('miDiv').style.display = 'block';
        }
        function eliminarDiv() {
            var div = document.getElementById('miDiv');
            if (div) {
                div.parentNode.removeChild(div);
            }
        }
    </script>
</head>
<body>
    <h1>Ejemplo JSP con Botones</h1>
    <button onclick="mostrarDiv()">Mostrar Div</button>
    <button onclick="eliminarDiv()">Eliminar Div</button>
    <div id="miDiv">
        <p>Este es el contenido del div.</p>
    </div>
</body>
</html>