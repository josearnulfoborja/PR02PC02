package com.mycompany.parcial2.servlet;

import com.mycompany.parcial2.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet SrvUsuario
 * Maneja las peticiones relacionadas con usuarios (registro, login, etc.)
 */
@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {

    /**
     * Maneja peticiones POST para registro de usuarios
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar response para JSON y UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Configurar headers CORS si es necesario
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        
        // Obtener parámetro de acción para determinar qué operación realizar
        String action = request.getParameter("action");
        
        try {
            if ("verificarCorreo".equals(action)) {
                // Manejar verificación de correo duplicado
                manejarVerificacionCorreo(request, response, out, gson, jsonResponse);
                return;
            }
            
            // Si no es verificación de correo, proceder con registro normal
            // Leer datos JSON del request
            StringBuilder jsonString = new StringBuilder();
            String line;
            
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
            }
            
            System.out.println("Datos recibidos: " + jsonString.toString());
            
            // Parsear JSON a objeto
            JsonObject datosUsuario = gson.fromJson(jsonString.toString(), JsonObject.class);
            
            // Validar que se recibieron datos
            if (datosUsuario == null) {
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "No se recibieron datos");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            // Crear objeto Usuario y asignar valores mediante setters
            Usuario usuario = new Usuario();
            
            // Asignar datos validados mediante setters
            if (datosUsuario.has("nombre") && !datosUsuario.get("nombre").isJsonNull()) {
                usuario.setNombre(datosUsuario.get("nombre").getAsString());
            }
            
            if (datosUsuario.has("apellido") && !datosUsuario.get("apellido").isJsonNull()) {
                usuario.setApellido(datosUsuario.get("apellido").getAsString());
            }
            
            if (datosUsuario.has("correo") && !datosUsuario.get("correo").isJsonNull()) {
                usuario.setCorreo(datosUsuario.get("correo").getAsString());
            }
            
            if (datosUsuario.has("telefono") && !datosUsuario.get("telefono").isJsonNull()) {
                usuario.setTelefono(datosUsuario.get("telefono").getAsString());
            }
            
            if (datosUsuario.has("clave") && !datosUsuario.get("clave").isJsonNull()) {
                String claveOriginal = datosUsuario.get("clave").getAsString();
                // Encriptar la clave en MD5 antes de guardarla
                String claveEncriptada = Usuario.encriptarMD5(claveOriginal);
                usuario.setClave(claveEncriptada);
            }
            
            // Obtener dirección IP del cliente
            String direccionIP = obtenerDireccionIP(request);
            usuario.setDireccionIP(direccionIP);
            
            // Generar código de verificación
            usuario.setCodigoVerificacion(usuario.generarCodigoVerificacion());
            
            System.out.println("Registrando usuario: " + usuario.getCorreo() + " desde IP: " + direccionIP);
            
            // Validar campos obligatorios
            if (usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getApellido() == null || usuario.getApellido().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().isEmpty() ||
                usuario.getTelefono() == null || usuario.getTelefono().isEmpty() ||
                usuario.getClave() == null || usuario.getClave().isEmpty()) {
                
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "Todos los campos son obligatorios");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            // Verificar si el correo ya existe
            if (Usuario.correoExiste(usuario.getCorreo())) {
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "El correo electrónico ya está registrado");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.print(gson.toJson(jsonResponse));
                System.out.println("Intento de registro con correo existente: " + usuario.getCorreo());
                return;
            }
            
            // Llamar al método de registro
            boolean registroExitoso = usuario.registrarUsuario();
            
            // Preparar respuesta JSON
            if (registroExitoso) {
                jsonResponse.addProperty("estado", "OK");
                jsonResponse.addProperty("mensaje", "Registro exitoso");
                jsonResponse.addProperty("usuario", usuario.getCorreo());
                response.setStatus(HttpServletResponse.SC_OK);
                System.out.println("Registro exitoso para: " + usuario.getCorreo());
            } else {
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "No se pudo registrar");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                System.err.println("Error en registro para: " + usuario.getCorreo());
            }
            
        } catch (JsonSyntaxException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
            jsonResponse.addProperty("estado", "ERROR");
            jsonResponse.addProperty("mensaje", "Formato JSON inválido");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.err.println("Error interno del servidor: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.addProperty("estado", "ERROR");
            jsonResponse.addProperty("mensaje", "Error interno del servidor");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // Enviar respuesta
            out.print(gson.toJson(jsonResponse));
            out.flush();
        }
    }
    
    /**
     * Maneja peticiones OPTIONS para CORS
     */
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    /**
     * Obtiene la dirección IP real del cliente
     * @param request petición HTTP
     * @return dirección IP del cliente
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
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // Si hay múltiples IPs, tomar la primera
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        // Convertir IPv6 localhost a IPv4 localhost para mejor legibilidad
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }
        
        // Si la IP está vacía o es null, asignar localhost
        if (ip == null || ip.trim().isEmpty()) {
            ip = "127.0.0.1";
        }
        
        return ip.trim();
    }
    
    /**
     * Maneja la verificación de correo duplicado
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @param out PrintWriter para respuesta
     * @param gson instancia de Gson
     * @param jsonResponse objeto JSON de respuesta
     * @throws IOException
     */
    private void manejarVerificacionCorreo(HttpServletRequest request, HttpServletResponse response, 
            PrintWriter out, Gson gson, JsonObject jsonResponse) throws IOException {
        
        try {
            // Leer datos JSON del request
            StringBuilder jsonString = new StringBuilder();
            String line;
            
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
            }
            
            // Parsear JSON
            JsonObject datos = gson.fromJson(jsonString.toString(), JsonObject.class);
            
            if (datos == null || !datos.has("correo")) {
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "Correo no proporcionado");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson(jsonResponse));
                return;
            }
            
            String correo = datos.get("correo").getAsString();
            
            // Verificar si el correo existe
            boolean existe = Usuario.correoExiste(correo);
            
            // Preparar respuesta
            jsonResponse.addProperty("estado", "OK");
            jsonResponse.addProperty("existe", existe);
            jsonResponse.addProperty("mensaje", existe ? "El correo ya está registrado" : "El correo está disponible");
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(gson.toJson(jsonResponse));
            
        } catch (Exception e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.addProperty("estado", "ERROR");
            jsonResponse.addProperty("mensaje", "Error interno del servidor");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson(jsonResponse));
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet para manejo de usuarios - Registro y autenticación";
    }
}