package com.mycompany.pr02pc02;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.pr02pc02.models.Usuario;
import com.mycompany.pr02pc02.utils.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Servlet para manejar el login de usuarios
 * @author Usuario
 */
@WebServlet(name = "SrvLogin", urlPatterns = {"/SrvLogin"})
public class SrvLogin extends HttpServlet {
    
    /**
     * Maneja las peticiones POST para el login de usuarios
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar respuesta
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // Objeto para la respuesta JSON
        JsonObject jsonResponse = new JsonObject();
        
        try {
            // Leer JSON de la petición
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                jsonBuilder.append(line);
            }
            
            if (jsonBuilder.length() == 0) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "No se recibieron datos");
                out.print(jsonResponse.toString());
                return;
            }
            
            // Parsear JSON
            Gson gson = new Gson();
            JsonObject jsonData = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
            
            // Obtener datos del usuario
            String correo = jsonData.get("correo").getAsString().trim().toLowerCase();
            String clave = jsonData.get("clave").getAsString();
            
            // Validar datos
            if (correo.isEmpty() || clave.isEmpty()) {
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "Todos los campos son obligatorios");
                out.print(jsonResponse.toString());
                return;
            }
            
            // Encriptar contraseña
            String claveEncriptada = encriptarMD5(clave);
            
            // Debug: Log de información
            System.out.println("=== DEBUG LOGIN ===");
            System.out.println("Correo recibido: " + correo);
            System.out.println("Clave original: " + clave);
            System.out.println("Clave encriptada: " + claveEncriptada);
            
            // Intentar login
            Usuario usuario = validarCredenciales(correo, claveEncriptada);
            
            if (usuario != null) {
                // Login exitoso - crear sesión
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuario);
                session.setAttribute("usuarioId", usuario.getIdUsuario());
                session.setAttribute("usuarioNombre", usuario.getNombre());
                session.setMaxInactiveInterval(30 * 60); // 30 minutos
                
                // Registrar acceso
                registrarAcceso(usuario.getIdUsuario(), request);
                
                // Respuesta exitosa
                jsonResponse.addProperty("estado", true);
                jsonResponse.addProperty("mensaje", "Login exitoso");
                
                // Agregar datos del usuario (sin contraseña)
                JsonObject usuarioJson = new JsonObject();
                usuarioJson.addProperty("id", usuario.getIdUsuario());
                usuarioJson.addProperty("nombre", usuario.getNombre());
                usuarioJson.addProperty("apellido", usuario.getApellido());
                usuarioJson.addProperty("correo", usuario.getCorreo());
                usuarioJson.addProperty("telefono", usuario.getTelefono());
                
                jsonResponse.add("usuario", usuarioJson);
                
            } else {
                // Credenciales inválidas - agregar más información de debug
                jsonResponse.addProperty("estado", false);
                jsonResponse.addProperty("mensaje", "Correo o contraseña incorrectos");
                
                // Debug: Información adicional (remover en producción)
                jsonResponse.addProperty("debug_correo", correo);
                jsonResponse.addProperty("debug_clave_md5", claveEncriptada);
                System.out.println("=== LOGIN FALLIDO ===");
                System.out.println("No se encontró usuario con correo: " + correo);
                System.out.println("Y clave MD5: " + claveEncriptada);
            }
            
        } catch (Exception e) {
            // Error interno
            jsonResponse.addProperty("estado", false);
            jsonResponse.addProperty("mensaje", "Error interno del servidor");
            
            // Log del error
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.println("Error en SrvLogin: " + sw.toString());
        } finally {
            out.print(jsonResponse.toString());
            out.close();
        }
    }
    
    /**
     * Valida las credenciales del usuario contra la base de datos
     * @param correo correo del usuario
     * @param claveEncriptada contraseña encriptada en MD5
     * @return objeto Usuario si las credenciales son válidas, null en caso contrario
     */
    private Usuario validarCredenciales(String correo, String claveEncriptada) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conn = Conexion.getConnection();
            
            String sql = "SELECT id_usuario, nombre, apellido, correo, telefono " +
                        "FROM usuarios " +
                        "WHERE correo = ? AND clave = ? AND estado = TRUE";
            
            System.out.println("=== DEBUG QUERY ===");
            System.out.println("SQL: " + sql);
            System.out.println("Parámetro 1 (correo): " + correo);
            System.out.println("Parámetro 2 (clave): " + claveEncriptada);
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, claveEncriptada);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                System.out.println("=== USUARIO ENCONTRADO ===");
                System.out.println("ID: " + usuario.getIdUsuario());
                System.out.println("Nombre: " + usuario.getNombre());
            } else {
                System.out.println("=== NO SE ENCONTRÓ USUARIO ===");
                
                // Debug: Verificar si existe el correo
                String checkEmailSql = "SELECT correo, clave, estado FROM usuarios WHERE correo = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkEmailSql)) {
                    checkStmt.setString(1, correo);
                    try (ResultSet checkRs = checkStmt.executeQuery()) {
                        if (checkRs.next()) {
                            System.out.println("Correo encontrado en DB: " + checkRs.getString("correo"));
                            System.out.println("Clave en DB: " + checkRs.getString("clave"));
                            System.out.println("Estado: " + checkRs.getBoolean("estado"));
                        } else {
                            System.out.println("Correo NO encontrado en la base de datos");
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error validando credenciales: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexiones: " + e.getMessage());
            }
        }
        
        return usuario;
    }
    
    /**
     * Registra el acceso del usuario en el log de auditoría
     * @param usuarioId ID del usuario
     * @param request petición HTTP para obtener IP y otros datos
     */
    private void registrarAcceso(int usuarioId, HttpServletRequest request) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnection();
            
            String sql = "INSERT INTO log_usuarios (usuario_id, accion, ip_cliente, user_agent) " +
                        "VALUES (?, 'LOGIN', ?, ?)";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            stmt.setString(2, obtenerIPCliente(request));
            stmt.setString(3, request.getHeader("User-Agent"));
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Error registrando acceso: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.err.println("Error cerrando conexiones en registrarAcceso: " + e.getMessage());
            }
        }
    }
    
    /**
     * Encripta una cadena usando MD5
     * @param texto texto a encriptar
     * @return texto encriptado en MD5
     */
    private String encriptarMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error encriptando MD5: " + e.getMessage());
            return texto; // Fallback inseguro
        }
    }
    
    /**
     * Obtiene la dirección IP del cliente
     * @param request petición HTTP
     * @return dirección IP del cliente
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
        
        // Si hay múltiples IPs, tomar la primera
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip != null ? ip : "127.0.0.1";
    }
    
    /**
     * Maneja las peticiones GET (no permitidas para login)
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.setContentType("application/json;charset=UTF-8");
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("estado", false);
        jsonResponse.addProperty("mensaje", "Método GET no permitido para login");
        
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.close();
    }
    
    /**
     * Información del servlet
     * @return descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet para manejar el login de usuarios - Sistema de Reservas de Hotel";
    }
}