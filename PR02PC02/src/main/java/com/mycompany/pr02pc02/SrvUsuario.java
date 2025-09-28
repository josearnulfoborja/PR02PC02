package com.mycompany.pr02pc02;

import com.mycompany.pr02pc02.model.Usuario;
import com.mycompany.pr02pc02.utils.Conexion;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        try {
            // Leer JSON del cliente
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);

            // Validar datos del usuario
            if (usuario.getNombre() == null || usuario.getCorreo() == null || usuario.getClave() == null) {
                out.print(gson.toJson(new Respuesta("ERROR", "Datos incompletos")));
                return;
            }

            // Encriptar clave en MD5
            usuario.setClave(encriptarMD5(usuario.getClave()));

            // Obtener dirección IP del cliente
            String ipCliente = request.getRemoteAddr();
            usuario.setDireccionIP(ipCliente);

            // Registrar usuario en la base de datos
            boolean registrado = usuario.registrarUsuario();

            if (registrado) {
                out.print(gson.toJson(new Respuesta("OK", "Registro exitoso")));
            } else {
                out.print(gson.toJson(new Respuesta("ERROR", "No se pudo registrar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(new Respuesta("ERROR", "Error en el servidor: " + e.getMessage())));
        }
    }

    private String encriptarMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : messageDigest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static class Respuesta {
        private String estado;
        private String mensaje;

        public Respuesta(String estado, String mensaje) {
            this.estado = estado;
            this.mensaje = mensaje;
        }

        public String getEstado() {
            return estado;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}