package com.mycompany.pr02pc02.servlets;

import com.google.gson.Gson;
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
 */
@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {
    
    private final Gson gson = new Gson();
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * Procesa el registro de nuevos usuarios
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
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
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        
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
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // Si hay múltiples IPs, tomar la primera
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
    
    /**
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
    }
}