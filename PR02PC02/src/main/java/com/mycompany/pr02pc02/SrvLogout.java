package com.mycompany.pr02pc02;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para manejar el logout (cerrar sesión) de usuarios
 * @author Usuario
 */
@WebServlet(name = "SrvLogout", urlPatterns = {"/SrvLogout"})
public class SrvLogout extends HttpServlet {
    
    /**
     * Maneja las peticiones GET y POST para cerrar sesión
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de E/S
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalidar la sesión
            session.invalidate();
        }
        
        // Redireccionar al login con mensaje de logout exitoso
        response.sendRedirect("login.jsp?logout=exitoso");
    }
    
    /**
     * Maneja las peticiones GET
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Maneja las peticiones POST
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException si ocurre un error de E/S
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Información del servlet
     * @return descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet para cerrar sesión de usuarios - Sistema de Reservas de Hotel";
    }
}