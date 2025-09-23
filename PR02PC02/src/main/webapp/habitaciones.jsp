<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Habitaciones - Sistema de Reservas</title>
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
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 2.5em;
            margin-bottom: 10px;
        }
        
        .header p {
            font-size: 1.1em;
            opacity: 0.9;
        }
        
        .navigation {
            padding: 20px 30px;
            background: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
        }
        
        .btn {
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
            transition: transform 0.2s ease;
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn-secondary {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
        }
        
        .content {
            padding: 30px;
        }
        
        .rooms-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 30px;
        }
        
        .room-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s ease;
        }
        
        .room-card:hover {
            transform: translateY(-5px);
        }
        
        .room-image {
            height: 200px;
            background: linear-gradient(45deg, #667eea, #764ba2);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            color: white;
        }
        
        .room-info {
            padding: 20px;
        }
        
        .room-title {
            font-size: 1.4em;
            font-weight: bold;
            margin-bottom: 10px;
            color: #333;
        }
        
        .room-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 15px;
        }
        
        .room-features {
            list-style: none;
            margin-bottom: 20px;
        }
        
        .room-features li {
            padding: 5px 0;
            color: #555;
        }
        
        .room-features li::before {
            content: "✓";
            color: #28a745;
            font-weight: bold;
            margin-right: 10px;
        }
        
        .room-price {
            font-size: 1.5em;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 15px;
        }
        
        .btn-reserve {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.2s ease;
        }
        
        .btn-reserve:hover {
            transform: translateY(-2px);
        }
        
        .availability-status {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9em;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .available {
            background: #d4edda;
            color: #155724;
        }
        
        .occupied {
            background: #f8d7da;
            color: #721c24;
        }
        
        @media (max-width: 768px) {
            .rooms-grid {
                grid-template-columns: 1fr;
            }
            
            .container {
                margin: 10px;
            }
            
            .header {
                padding: 20px;
            }
            
            .header h1 {
                font-size: 2em;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🛏️ Nuestras Habitaciones</h1>
            <p>Descubre nuestras cómodas y elegantes habitaciones</p>
        </div>
        
        <div class="navigation">
            <a href="index.html" class="btn">🏠 Inicio</a>
            <a href="registro.jsp" class="btn btn-secondary">👤 Registro</a>
        </div>
        
        <div class="content">
            <p style="text-align: center; font-size: 1.1em; color: #666; margin-bottom: 20px;">
                Selecciona la habitación que mejor se adapte a tus necesidades
            </p>
            
            <div class="rooms-grid">
                <!-- Habitación Individual -->
                <div class="room-card">
                    <div class="room-image">🛏️</div>
                    <div class="room-info">
                        <div class="availability-status available">Disponible</div>
                        <h3 class="room-title">Habitación Individual</h3>
                        <p class="room-description">
                            Perfecta para una persona. Cómoda y acogedora con todas las comodidades necesarias para una estancia placentera.
                        </p>
                        <ul class="room-features">
                            <li>1 Cama individual</li>
                            <li>Baño privado</li>
                            <li>WiFi gratuito</li>
                            <li>TV por cable</li>
                            <li>Aire acondicionado</li>
                            <li>Escritorio de trabajo</li>
                        </ul>
                        <div class="room-price">$75 / noche</div>
                        <button class="btn-reserve" onclick="reservarHabitacion('individual')">
                            Reservar Ahora
                        </button>
                    </div>
                </div>
                
                <!-- Habitación Doble -->
                <div class="room-card">
                    <div class="room-image">🛏️🛏️</div>
                    <div class="room-info">
                        <div class="availability-status available">Disponible</div>
                        <h3 class="room-title">Habitación Doble</h3>
                        <p class="room-description">
                            Ideal para parejas o dos personas. Espaciosa y confortable con excelentes vistas y servicios premium.
                        </p>
                        <ul class="room-features">
                            <li>1 Cama matrimonial</li>
                            <li>Baño privado con bañera</li>
                            <li>WiFi gratuito</li>
                            <li>TV Smart</li>
                            <li>Aire acondicionado</li>
                            <li>Mini bar</li>
                            <li>Balcón con vista</li>
                        </ul>
                        <div class="room-price">$120 / noche</div>
                        <button class="btn-reserve" onclick="reservarHabitacion('doble')">
                            Reservar Ahora
                        </button>
                    </div>
                </div>
                
                <!-- Suite Familiar -->
                <div class="room-card">
                    <div class="room-image">🏠</div>
                    <div class="room-info">
                        <div class="availability-status available">Disponible</div>
                        <h3 class="room-title">Suite Familiar</h3>
                        <p class="room-description">
                            Perfecta para familias. Amplio espacio con área de estar separada y todas las comodidades para una estancia memorable.
                        </p>
                        <ul class="room-features">
                            <li>1 Cama matrimonial + 2 individuales</li>
                            <li>2 Baños privados</li>
                            <li>WiFi gratuito</li>
                            <li>TV Smart en cada habitación</li>
                            <li>Aire acondicionado</li>
                            <li>Kitchenette</li>
                            <li>Área de estar</li>
                            <li>Terraza privada</li>
                        </ul>
                        <div class="room-price">$200 / noche</div>
                        <button class="btn-reserve" onclick="reservarHabitacion('suite')">
                            Reservar Ahora
                        </button>
                    </div>
                </div>
                
                <!-- Suite Presidencial -->
                <div class="room-card">
                    <div class="room-image">👑</div>
                    <div class="room-info">
                        <div class="availability-status occupied">Ocupada</div>
                        <h3 class="room-title">Suite Presidencial</h3>
                        <p class="room-description">
                            La máxima expresión de lujo y confort. Servicios exclusivos y amenidades de primera clase para una experiencia inolvidable.
                        </p>
                        <ul class="room-features">
                            <li>1 Cama King size</li>
                            <li>Baño de mármol con jacuzzi</li>
                            <li>WiFi premium</li>
                            <li>TV Smart 65"</li>
                            <li>Aire acondicionado dual</li>
                            <li>Bar completo</li>
                            <li>Sala de estar privada</li>
                            <li>Servicio de mayordomo 24/7</li>
                            <li>Terraza panorámica</li>
                        </ul>
                        <div class="room-price">$450 / noche</div>
                        <button class="btn-reserve" onclick="reservarHabitacion('presidencial')" disabled style="opacity: 0.6; cursor: not-allowed;">
                            No Disponible
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function reservarHabitacion(tipo) {
            // Verificar si el usuario está logueado (esto se mejoraría con sesiones reales)
            const estaLogueado = false; // Por ahora siempre false
            
            if (!estaLogueado) {
                if (confirm('Debes iniciar sesión para reservar una habitación. ¿Deseas ir al login?')) {
                    window.location.href = 'index.html';
                }
                return;
            }
            
            // Si estuviera logueado, proceder con la reserva
            alert(`Reservando habitación ${tipo}. Esta funcionalidad estará disponible próximamente.`);
        }
        
        // Mensaje de bienvenida si viene del registro
        window.addEventListener('load', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const fromRegistro = urlParams.get('from');
            
            if (fromRegistro === 'registro') {
                const mensaje = document.createElement('div');
                mensaje.style.cssText = `
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    background: #28a745;
                    color: white;
                    padding: 15px 20px;
                    border-radius: 8px;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                    z-index: 1000;
                    font-weight: bold;
                `;
                mensaje.textContent = '¡Explora nuestras habitaciones disponibles!';
                document.body.appendChild(mensaje);
                
                setTimeout(() => {
                    mensaje.remove();
                }, 5000);
            }
        });
    </script>
</body>
</html>