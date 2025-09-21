package com.mycompany.pr02pc02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "SrvUsuario", urlPatterns = {"/SrvUsuario"})
public class SrvUsuario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        Usuario usuario = gson.fromJson(sb.toString(), Usuario.class);
        // Encriptar clave en MD5
        usuario.setClave(md5(usuario.getClave()));
        // Obtener IP
        String ip = request.getRemoteAddr();
        usuario.setDireccionIP(ip);
        boolean exito = usuario.registrarUsuario();
        String estado = exito ? "OK" : "ERROR";
        String mensaje = exito ? "Registro exitoso" : "No se pudo registrar";
        out.print("{\"estado\":\"" + estado + "\",\"mensaje\":\"" + mensaje + "\"}");
        out.flush();
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
