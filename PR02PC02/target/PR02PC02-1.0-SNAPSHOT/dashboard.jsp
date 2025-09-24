<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.mycompany.pr02pc02.models.Usuario"%>

<%
    // Verificar si el usuario está logueado
    HttpSession userSession = request.getSession(false);
    Usuario usuario = null;
    
    if (userSession != null) {
        usuario = (Usuario) userSession.getAttribute("usuario");
    }
    
    // Si no hay usuario logueado, redireccionar al login
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Sistema de Reservas</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
        }
        
        .welcome-section h1 {
            font-size: 2.2em;
            margin-bottom: 10px;
        }
        
        .welcome-section p {
            font-size: 1.1em;
            opacity: 0.9;
        }
        
        .user-info {
            text-align: right;
            background: rgba(255, 255, 255, 0.1);
            padding: 15px;
            border-radius: 10px;
            backdrop-filter: blur(10px);
        }
        
        .user-name {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .user-email {
            opacity: 0.8;
            font-size: 0.9em;
        }
        
        .logout-btn {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            transition: all 0.3s ease;
        }
        
        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }
        
        .content {
            padding: 40px;
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-bottom: 30px;
        }
        
        .dashboard-card {
            background: #f8f9fa;
            border-radius: 15px;
            padding: 30px;
            text-align: center;
            transition: transform 0.3s ease;
            border: 1px solid #e9ecef;
        }
        
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        
        .dashboard-icon {
            font-size: 48px;
            margin-bottom: 20px;
        }
        
        .dashboard-card h3 {
            color: #333;
            font-size: 1.4em;
            margin-bottom: 15px;
        }
        
        .dashboard-card p {
            color: #666;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        
        .btn {
            padding: 12px 25px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: transform 0.2s ease;
        }
        
        .btn:hover {
            transform: translateY(-2px);
        }
        
        .btn-success {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
        }
        
        .btn-info {
            background: linear-gradient(135deg, #17a2b8 0%, #6f42c1 100%);
        }
        
        .btn-warning {
            background: linear-gradient(135deg, #ffc107 0%, #fd7e14 100%);
        }
        
        .quick-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            border-left: 4px solid #667eea;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
        }
        
        .stat-number {
            font-size: 2em;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 5px;
        }
        
        .stat-label {
            color: #666;
            font-size: 0.9em;
        }
        
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                text-align: center;
            }
            
            .user-info {
                margin-top: 20px;
                text-align: center;
            }
            
            .content {
                padding: 20px;
            }
            
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class="welcome-section">
                <h1>🏨 Bienvenido al Dashboard</h1>
                <p>Gestiona tus reservas y perfil desde aquí</p>
            </div>
            <div class="user-info">
                <div class="user-name">👤 <%= usuario.getNombre() %> <%= usuario.getApellido() %></div>
                <div class="user-email">📧 <%= usuario.getCorreo() %></div>
                <button class="logout-btn" onclick="cerrarSesion()">🚪 Cerrar Sesión</button>
            </div>
        </div>
        
        <div class="content">
            <!-- Estadísticas rápidas -->
            <div class="quick-stats">
                <div class="stat-card">
                    <div class="stat-number">0</div>
                    <div class="stat-label">Reservas Activas</div>
                </div>
                <div class="stat-card" style="border-left-color: #28a745;">
                    <div class="stat-number" style="color: #28a745;">3</div>
                    <div class="stat-label">Reservas Completadas</div>
                </div>
                <div class="stat-card" style="border-left-color: #ffc107;">
                    <div class="stat-number" style="color: #ffc107;">1</div>
                    <div class="stat-label">Reservas Pendientes</div>
                </div>
                <div class="stat-card" style="border-left-color: #dc3545;">
                    <div class="stat-number" style="color: #dc3545;">$450</div>
                    <div class="stat-label">Total Gastado</div>
                </div>
            </div>
            
            <!-- Opciones principales -->
            <div class="dashboard-grid">
                <div class="dashboard-card">
                    <div class="dashboard-icon">🛏️</div>
                    <h3>Explorar Habitaciones</h3>
                    <p>Descubre nuestras cómodas habitaciones y encuentra la perfecta para ti</p>
                    <a href="habitaciones.jsp" class="btn">Ver Habitaciones</a>
                </div>
                
                <div class="dashboard-card">
                    <div class="dashboard-icon">📋</div>
                    <h3>Mis Reservas</h3>
                    <p>Gestiona tus reservas actuales y revisa tu historial de estadías</p>
                    <button class="btn btn-success" onclick="alert('Funcionalidad próximamente')">Ver Reservas</button>
                </div>
                
                <div class="dashboard-card">
                    <div class="dashboard-icon">👤</div>
                    <h3>Mi Perfil</h3>
                    <p>Actualiza tu información personal y preferencias de cuenta</p>
                    <button class="btn btn-info" onclick="alert('Funcionalidad próximamente')">Editar Perfil</button>
                </div>
                
                <div class="dashboard-card">
                    <div class="dashboard-icon">💳</div>
                    <h3>Métodos de Pago</h3>
                    <p>Administra tus tarjetas y opciones de pago para reservas</p>
                    <button class="btn btn-warning" onclick="alert('Funcionalidad próximamente')">Configurar Pagos</button>
                </div>
                
                <div class="dashboard-card">
                    <div class="dashboard-icon">🎁</div>
                    <h3>Ofertas Especiales</h3>
                    <p>Descubre promociones exclusivas y descuentos disponibles</p>
                    <button class="btn" onclick="alert('Funcionalidad próximamente')">Ver Ofertas</button>
                </div>
                
                <div class="dashboard-card">
                    <div class="dashboard-icon">📞</div>
                    <h3>Soporte</h3>
                    <p>¿Necesitas ayuda? Contacta a nuestro equipo de atención al cliente</p>
                    <button class="btn" onclick="alert('Contacto: +1-800-HOTEL-01')">Contactar Soporte</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Función para cerrar sesión
        function cerrarSesion() {
            if (confirm('¿Estás seguro de que deseas cerrar sesión?')) {
                // Limpiar localStorage
                localStorage.removeItem('usuario');
                
                // Redireccionar al servlet de logout o directamente al login
                window.location.href = 'SrvLogout';
            }
        }
        
        // Verificar si hay mensajes de bienvenida
        window.addEventListener('load', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const loginExitoso = urlParams.get('login');
            
            if (loginExitoso === 'exitoso') {
                // Mostrar mensaje de bienvenida
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
                mensaje.textContent = '¡Sesión iniciada correctamente!';
                document.body.appendChild(mensaje);
                
                setTimeout(() => {
                    mensaje.remove();
                }, 5000);
                
                // Limpiar la URL
                window.history.replaceState({}, document.title, window.location.pathname);
            }
        });
        
        // Actualizar estadísticas dinámicamente (ejemplo)
        setTimeout(() => {
            // Aquí podrías hacer una petición AJAX para obtener estadísticas reales
            console.log('Estadísticas cargadas para el usuario: <%= usuario.getNombre() %>');
        }, 1000);
    </script>
</body>
</html>