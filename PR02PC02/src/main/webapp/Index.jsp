<!DOCTYPE html>
<html lang="es">
<head>
    <title>Sistema de Reservas de Hotel</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: white;
        }
        
        .container {
            text-align: center;
            padding: 40px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 15px;
            backdrop-filter: blur(10px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            max-width: 600px;
        }
        
        .logo {
            font-size: 48px;
            margin-bottom: 20px;
        }
        
        h1 {
            font-size: 2.5em;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        
        .description {
            font-size: 1.2em;
            margin-bottom: 40px;
            opacity: 0.9;
            line-height: 1.6;
        }
        
        .actions {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 15px 30px;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: bold;
            font-size: 16px;
            transition: all 0.3s ease;
            border: 2px solid rgba(255, 255, 255, 0.3);
        }
        
        .btn:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.2);
        }
        
        .btn-primary {
            background: rgba(255, 255, 255, 0.9);
            color: #667eea;
        }
        
        .btn-primary:hover {
            background: white;
            color: #667eea;
        }
        
        .features {
            margin-top: 40px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }
        
        .feature {
            padding: 20px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
            text-align: center;
        }
        
        .feature-icon {
            font-size: 32px;
            margin-bottom: 10px;
        }
        
        .feature h3 {
            margin-bottom: 10px;
            font-size: 1.2em;
        }
        
        .feature p {
            opacity: 0.8;
            font-size: 0.9em;
        }
        
        .footer {
            margin-top: 40px;
            opacity: 0.7;
            font-size: 0.9em;
        }
        
        @media (max-width: 768px) {
            .container {
                margin: 20px;
                padding: 30px 20px;
            }
            
            h1 {
                font-size: 2em;
            }
            
            .actions {
                flex-direction: column;
                align-items: center;
            }
            
            .btn {
                width: 200px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo"></div>
        <h1>Sistema de Reservas de Hotel</h1>
        <div class="description">
            Bienvenido a nuestro sistema integral de reservas de hotel. 
            Registre su cuenta para acceder a todas las funcionalidades 
            y realizar reservas de manera rápida y segura.
        </div>
        
        <div class="actions">
            <a href="registro.jsp" class="btn btn-primary">Registrarse</a>
            <a href="login.jsp" class="btn">Iniciar Session</a>
            <a href="habitaciones.jsp" class="btn">Ver Habitaciones</a>
        </div>
        
        <div class="features">
            <div class="feature">
                <div class="feature-icon"></div>
                <h3>Registro Seguro</h3>
                <p>Sistema de registro con validaciones y encriptacion</p>
            </div>                      
        </div>
        
        <div class="footer">
            <p>&copy; 2025 Sistema de Reservas de Hotel - Desarrollado con Jakarta EE</p>
        </div>
    </div>
</body>
</html>