# PR02PC02 - Sistema de Reservas de Hotel

Este proyecto es una aplicación Java basada en Jakarta EE que incluye un **módulo completo de registro de usuarios** para un sistema de reservas de hotel. El proyecto está organizado en capas: frontend (HTML, CSS, JavaScript), backend (Java con Servlets), y base de datos (MySQL).

## 🚀 Funcionalidades Desarrolladas

### Módulo de Registro de Usuarios
- ✅ Formulario de registro con validaciones completas
- ✅ Encriptación MD5 de contraseñas
- ✅ Validaciones tanto en frontend como backend
- ✅ Captcha simple para seguridad
- ✅ Respuestas JSON asíncronas
- ✅ Diseño responsive y moderno

## 📁 Estructura del Proyecto

```
PR02PC02/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mycompany/pr02pc02/
│   │   │       ├── servlets/
│   │   │       │   └── SrvUsuario.java          # Servlet principal
│   │   │       ├── models/
│   │   │       │   └── Usuario.java             # Modelo de datos
│   │   │       └── utils/
│   │   │           └── Conexion.java            # Conexión a BD
│   │   ├── resources/
│   │   │   └── database/
│   │   │       └── schema.sql                   # Script de BD
│   │   └── webapp/
│   │       ├── registro.jsp                     # Formulario de registro
│   │       ├── index.html                       # Página principal
│   │       ├── error/                           # Páginas de error
│   │       └── WEB-INF/
│   │           └── web.xml                      # Configuración web
├── pom.xml                                      # Dependencias Maven
└── README.md                                    # Este archivo
```

## 🛠️ Requisitos previos

- **Java JDK 8** o superior
- **Apache Maven 3.6+**
- **MySQL Server 5.7+** o **8.0+**
- **Servidor Jakarta EE** (Payara, GlassFish, TomEE, etc.)
- **Visual Studio Code** con extensiones Java

### Extensiones recomendadas para VS Code:
- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven)
- [Debugger for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug)

## 📦 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/josearnulfoborja/PR02PC02.git
cd PR02PC02
```

### 2. Configurar la base de datos

#### a) Crear la base de datos MySQL
```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script completo
source src/main/resources/database/schema.sql
```

#### b) Verificar la conexión
- Editar `src/main/java/com/mycompany/pr02pc02/utils/Conexion.java`
- Configurar las credenciales de MySQL:
```java
private static final String USUARIO = "tu_usuario_mysql";
private static final String PASSWORD = "tu_password_mysql";
```

### 3. Compilar el proyecto
```bash
# En VS Code Terminal o PowerShell
mvn clean compile
mvn package
```

### 4. Desplegar en servidor
```bash
# Copiar el WAR generado al servidor
copy target\PR02PC02-1.0-SNAPSHOT.war %SERVIDOR%\webapps\
```

## 🚀 Ejecución en VS Code

### Opción 1: Usando Maven
1. Abrir la carpeta del proyecto en VS Code
2. Abrir terminal integrada (`Ctrl+Shift+ñ`)
3. Ejecutar:
   ```bash
   mvn clean package
   ```
4. El archivo WAR estará en `target/`

### Opción 2: Usando servidor embebido
1. Instalar la extensión "Tomcat for Java"
2. Hacer clic derecho en el proyecto → "Run on Tomcat"
3. Acceder a: `http://localhost:8080/PR02PC02/`

## 🌐 URLs del Sistema

- **Página principal**: `http://localhost:8080/PR02PC02/`
- **Registro de usuarios**: `http://localhost:8080/PR02PC02/registro.jsp`
- **API de registro**: `http://localhost:8080/PR02PC02/SrvUsuario`

## 🧪 Datos de Prueba

El script SQL incluye usuarios de prueba:

| Email | Contraseña | Rol |
|-------|------------|-----|
| `admin@hotel.com` | `admin123` | ADMIN |
| `juan.perez@email.com` | `cliente123` | CLIENTE |
| `maria.lopez@hotel.com` | `empleado123` | EMPLEADO |

## 🔧 Características Técnicas

### Frontend
- **HTML5** semántico y accesible
- **CSS3** con gradientes y animaciones
- **JavaScript ES6+** con clases y arrow functions
- **Fetch API** para comunicación asíncrona
- **Validaciones** en tiempo real
- **Diseño responsive** para todos los dispositivos

### Backend
- **Jakarta EE 8** con Servlets
- **Patrón MVC** bien definido
- **Gson** para manejo de JSON
- **Encriptación MD5** para contraseñas
- **Conexiones JDBC** optimizadas
- **Manejo de errores** robusto

### Base de Datos
- **MySQL 8** con UTF8mb4
- **Índices** optimizados para consultas
- **Triggers** de auditoría
- **Procedimientos** almacenados
- **Vistas** para consultas frecuentes

## 📝 API Endpoints

### POST /SrvUsuario
Registra un nuevo usuario en el sistema.

**Request:**
```json
{
  "nombre": "JUAN",
  "apellido": "PÉREZ",
  "correo": "juan@email.com",
  "telefono": "1234567890",
  "clave": "password123",
  "estado": true
}
```

**Response (Éxito):**
```json
{
  "estado": true,
  "mensaje": "Usuario registrado exitosamente. Bienvenido JUAN!"
}
```

**Response (Error):**
```json
{
  "estado": false,
  "mensaje": "El correo electrónico ya está registrado"
}
```

## 🛡️ Validaciones Implementadas

### Frontend (JavaScript)
- ✅ Nombres y apellidos sin números
- ✅ Formato de email válido
- ✅ Teléfono de 8-15 dígitos
- ✅ Contraseña mínimo 6 caracteres
- ✅ Confirmación de contraseñas
- ✅ Captcha simple (H7B2K9)
- ✅ Campos obligatorios

### Backend (Java)
- ✅ Validación de datos recibidos
- ✅ Verificación de email único
- ✅ Encriptación MD5 segura
- ✅ Captura de IP del cliente
- ✅ Transacciones de base de datos
- ✅ Manejo de excepciones

## 🎨 Características de Diseño

- **Gradientes** modernos y atractivos
- **Animaciones** suaves en hover
- **Formulario** con retroalimentación visual
- **Mensajes** de error específicos por campo
- **Diseño** responsivo para móviles
- **Colores** consistentes en toda la aplicación

## 🚨 Solución de Problemas

### Error de Conexión a MySQL
1. Verificar que MySQL esté ejecutándose
2. Comprobar credenciales en `Conexion.java`
3. Asegurar que existe la base de datos `plataforma`

### Error 404 en Servlets
1. Verificar que el servlet esté anotado con `@WebServlet`
2. Comprobar la URL en el JavaScript
3. Verificar que el WAR esté desplegado correctamente

### Error de Compilación
1. Verificar versión de Java (mínimo JDK 8)
2. Ejecutar `mvn clean install`
3. Verificar dependencias en `pom.xml`

## 🤝 Contribución

1. Fork el proyecto
2. Crear branch para nueva funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push al branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autores

- **José Arnulfo Borja** - *Desarrollo inicial* - [josearnulfoborja](https://github.com/josearnulfoborja)

## 📞 Soporte

Para soporte técnico o preguntas sobre el proyecto:
- 📧 Email: soporte@sistemareservas.com
- 📱 Teléfono: +1 (555) 123-4567
- 💬 Discord: [Sistema Reservas](https://discord.gg/sistemareservas)

---

> **Nota**: Este README está optimizado para usuarios de Visual Studio Code que no cuentan con NetBeans. El proyecto está completamente funcional y listo para desarrollo/producción.
