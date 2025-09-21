package com.mycompany.pr02pc02.servlets;

import com.google.gson.Gson;
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
 */
@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP <code>POST</code> method.
     * Recibe los datos del usuario en formato JSON, los valida, 
     * encripta la contraseña y los guarda en la base de datos.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
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
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // Si hay múltiples IPs separadas por comas, tomar la primera
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
    
    /**
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
    }
}