package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import conexion.Conexion;
import modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;

@WebServlet("/SrvUsuario")
public class SrvUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            // Leer JSON recibido desde Fetch
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Convertir JSON a objeto Usuario
            Usuario usuario = gson.fromJson(sb.toString(), Usuario.class);

            // Encriptar clave con MD5
            usuario.setClave(encriptarMD5(usuario.getClave()));

            // Guardar dirección IP del cliente
            String ipCliente = request.getRemoteAddr();
            usuario.setDireccionIP(ipCliente);

            // Insertar en la BD
            boolean registrado = usuario.registrarUsuario();

            if (registrado) {
                jsonResponse.addProperty("estado", "OK");
                jsonResponse.addProperty("mensaje", "Registro exitoso 🎉");
            } else {
                jsonResponse.addProperty("estado", "ERROR");
                jsonResponse.addProperty("mensaje", "No se pudo registrar 😢");
            }

        } catch (Exception e) {
            jsonResponse.addProperty("estado", "ERROR");
            jsonResponse.addProperty("mensaje", "Excepción: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver JSON al cliente
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }

    // Método para encriptar la clave en MD5
    private String encriptarMD5(String clave) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(clave.getBytes("UTF-8"));

        // Convertir bytes a HEX
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
