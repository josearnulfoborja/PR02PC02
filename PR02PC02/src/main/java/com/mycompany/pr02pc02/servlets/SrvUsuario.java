package com.mycompany.pr02pc02.servlets;

import com.google.gson.Gson;
<<<<<<< HEAD
import com.google.gson.JsonObject;
import com.mycompany.pr02pc02.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet SrvUsuario para manejar el registro de usuarios
 * Procesa requests JSON y devuelve respuestas JSON
=======
import com.mycompany.pr02pc02.models.Usuario;
import com.mycompany.pr02pc02.utils.Conexion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para manejar el registro de usuarios
 * Recibe datos en formato JSON y los procesa para registrar un nuevo usuario
>>>>>>> dev
 */
@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {
    
<<<<<<< HEAD
    private final Gson gson = new Gson();
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * Procesa el registro de nuevos usuarios
=======
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP <code>POST</code> method.
     * Recibe los datos del usuario en formato JSON, los valida, 
     * encripta la contraseña y los guarda en la base de datos.
>>>>>>> dev
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
<<<<<<< HEAD
        // Configurar response para JSON
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();
        
        try {
            // Leer JSON del request
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            String jsonString = sb.toString();
            System.out.println("JSON recibido: " + jsonString);
            
            if (jsonString.isEmpty()) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "No se recibieron datos");
=======
        // Configurar la respuesta para JSON
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            // Leer el JSON del request
            StringBuilder jsonString = new StringBuilder();
            String line;
            
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
            }
            
            // Convertir JSON a objeto usando Gson
            Gson gson = new Gson();
            Map<String, Object> userData = gson.fromJson(jsonString.toString(), Map.class);
            
            // Validar que se recibieron los datos
            if (userData == null || userData.isEmpty()) {
                jsonResponse.put("estado", false);
                jsonResponse.put("mensaje", "No se recibieron datos válidos");
>>>>>>> dev
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
<<<<<<< HEAD
            // Parsear JSON a objeto
            JsonObject jsonInput = gson.fromJson(jsonString, JsonObject.class);
            
            // Crear instancia de Usuario
            Usuario usuario = new Usuario();
            
            // Asignar valores del JSON al modelo Usuario
            if (jsonInput.has("nombre") && !jsonInput.get("nombre").isJsonNull()) {
                usuario.setNombre(jsonInput.get("nombre").getAsString());
            }
            
            if (jsonInput.has("apellido") && !jsonInput.get("apellido").isJsonNull()) {
                usuario.setApellido(jsonInput.get("apellido").getAsString());
            }
            
            if (jsonInput.has("correo") && !jsonInput.get("correo").isJsonNull()) {
                usuario.setCorreo(jsonInput.get("correo").getAsString());
            }
            
            if (jsonInput.has("telefono") && !jsonInput.get("telefono").isJsonNull()) {
                usuario.setTelefono(jsonInput.get("telefono").getAsString());
            }
            
            if (jsonInput.has("clave") && !jsonInput.get("clave").isJsonNull()) {
                usuario.setClave(jsonInput.get("clave").getAsString());
            }
            
            if (jsonInput.has("estado") && !jsonInput.get("estado").isJsonNull()) {
                usuario.setEstado(jsonInput.get("estado").getAsBoolean());
            }
            
            // Obtener dirección IP del cliente
            String clientIP = obtenerDireccionIP(request);
            usuario.setDireccionIP(clientIP);
            
            // Generar código de verificación
            usuario.setCodigoVerificacion(usuario.generarCodigoVerificacion());
            
            System.out.println("Usuario a registrar: " + usuario.toString());
            
            // Validaciones básicas en el servidor
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "El nombre es obligatorio");
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "El apellido es obligatorio");
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            if (usuario.getCorreo() == null || !Usuario.validarEmail(usuario.getCorreo())) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "El correo electrónico no es válido");
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            if (usuario.getTelefono() == null || !Usuario.validarTelefono(usuario.getTelefono())) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "El teléfono no es válido");
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            if (usuario.getClave() == null || usuario.getClave().length() < 6) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "La contraseña debe tener al menos 6 caracteres");
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            // Intentar registrar usuario
            boolean registroExitoso = usuario.registrarUsuario();
            
            if (registroExitoso) {
                jsonResponse.addProperty("estado", true);
                jsonResponse.addProperty("mensaje", 
                    "Usuario registrado exitosamente. ¡Bienvenido " + usuario.getNombre() + "!");
                
                System.out.println("Registro exitoso para: " + usuario.getCorreo());
                
            } else {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", 
                    "Error al registrar usuario. Es posible que el correo ya esté registrado.");
                
                System.err.println("Fallo en registro para: " + usuario.getCorreo());
            }
            
        } catch (com.google.gson.JsonSyntaxException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            jsonResponse.addProperty("estado", false);
            jsonResponse.addProperty("mensaje", "Error en el formato de datos enviados");
            
        } catch (Exception e) {
            System.err.println("Error general en SrvUsuario: " + e.getMessage());
            e.printStackTrace();
            
            jsonResponse.addProperty("estado", false);
            jsonResponse.addProperty("mensaje", "Error interno del servidor");
        }
        
        // Enviar respuesta JSON
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
    
    /**
     * Método para obtener la dirección IP real del cliente
     * Considera proxies y load balancers
     */
    private String obtenerDireccionIP(HttpServletRequest request) {
=======
            // Crear instancia del modelo Usuario
            Usuario usuario = new Usuario();
            
            // Asignar valores usando setters
            usuario.setNombre((String) userData.get("nombre"));
            usuario.setApellido((String) userData.get("apellido"));
            usuario.setCorreo((String) userData.get("correo"));
            usuario.setTelefono((String) userData.get("telefono"));
            
            // Encriptar la contraseña en MD5
            String claveOriginal = (String) userData.get("clave");
            String claveEncriptada = encriptarMD5(claveOriginal);
            usuario.setClave(claveEncriptada);
            
            // Obtener la IP del cliente
            String direccionIP = obtenerIPCliente(request);
            usuario.setDireccionIP(direccionIP);
            
            // Establecer valores por defecto
            usuario.setRoll("CLIENTE"); // Rol por defecto para usuarios registrados
            usuario.setEstado(true); // Usuario activo por defecto
            usuario.setCodigoVerificacion(generarCodigoVerificacion());
            usuario.setModoAutenticacion("EMAIL");
            usuario.setFechaCreacion(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            // Registrar el usuario en la base de datos
            boolean registroExitoso = usuario.registrarUsuario();
            
            if (registroExitoso) {
                jsonResponse.put("estado", true);
                jsonResponse.put("mensaje", "Usuario registrado exitosamente. Bienvenido " + usuario.getNombre() + "!");
                jsonResponse.put("redirect", "index.html?registro=exitoso");
            } else {
                jsonResponse.put("estado", false);
                jsonResponse.put("mensaje", "Error al registrar el usuario. Intente nuevamente.");
            }
            
        } catch (Exception e) {
            // Manejar errores generales
            jsonResponse.put("estado", false);
            jsonResponse.put("mensaje", "Error interno del servidor: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Enviar respuesta JSON
            Gson gson = new Gson();
            out.print(gson.toJson(jsonResponse));
            out.flush();
        }
    }
    
    /**
     * Encripta una cadena usando MD5
     * @param texto Texto a encriptar
     * @return Texto encriptado en MD5
     */
    private String encriptarMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(texto.getBytes());
            
            // Convertir byte array a formato hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return texto; // En caso de error, devolver texto original
        }
    }
    
    /**
     * Obtiene la dirección IP real del cliente
     * Considera proxies y load balancers
     * @param request HttpServletRequest
     * @return Dirección IP del cliente
     */
    private String obtenerIPCliente(HttpServletRequest request) {
>>>>>>> dev
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
<<<<<<< HEAD
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        
=======
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
>>>>>>> dev
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
<<<<<<< HEAD
        // Si hay múltiples IPs, tomar la primera
=======
        // Si hay múltiples IPs separadas por comas, tomar la primera
>>>>>>> dev
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
    
    /**
<<<<<<< HEAD
     * Handles the HTTP <code>GET</code> method.
     * Devuelve información sobre el servlet
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        JsonObject info = new JsonObject();
        info.addProperty("servlet", "SrvUsuario");
        info.addProperty("descripcion", "Servlet para registro de usuarios del sistema de reservas de hotel");
        info.addProperty("metodos", "POST para registro, GET para información");
        info.addProperty("version", "1.0");
        info.addProperty("desarrollador", "Borja y equipo");
        
        out.print(gson.toJson(info));
        out.flush();
    }
    
    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet SrvUsuario - Registro de usuarios para sistema de reservas de hotel";
=======
     * Genera un código de verificación aleatorio
     * @return Código de 6 dígitos
     */
    private String generarCodigoVerificacion() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para registro de usuarios del sistema de reservas de hotel";
>>>>>>> dev
    }
}