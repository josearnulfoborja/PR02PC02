package com.mycompany.pr02pc02.test;

import com.mycompany.pr02pc02.models.Usuario;
import com.mycompany.pr02pc02.utils.Conexion;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase de prueba para simular el login y ver los logs
 */
public class TestLogin {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE LOGIN - SIMULANDO SERVLET ===");
        
        // Datos de prueba
        String correoTest = "test@hotel.com";
        String claveTest = "123456";
        
        System.out.println("Intentando login con:");
        System.out.println("- Email: " + correoTest);
        System.out.println("- Password: " + claveTest);
        System.out.println();
        
        // Simular el proceso de login
        testLogin(correoTest, claveTest);
        
        // Probar también con datos incorrectos
        System.out.println("\n=== PRUEBA CON DATOS INCORRECTOS ===");
        testLogin("usuario@inexistente.com", "password123");
    }
    
    private static void testLogin(String correo, String clave) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            System.out.println("1. Conectando a la base de datos...");
            con = Conexion.getConnection();
            
            if (con == null) {
                System.err.println("ERROR: No se pudo establecer conexión con la base de datos");
                return;
            }
            
            System.out.println("✓ Conexión establecida correctamente");
            
            // Encriptar contraseña
            System.out.println("2. Encriptando contraseña...");
            String claveEncriptada = encriptarMD5(clave);
            System.out.println("✓ Contraseña encriptada: " + claveEncriptada);
            
            // Preparar consulta
            System.out.println("3. Preparando consulta SQL...");
            String sql = "SELECT id_usuario, nombre, apellido, correo, telefono, estado " +
                        "FROM usuarios WHERE correo = ? AND clave = ? AND estado = 1";
            System.out.println("SQL: " + sql);
            System.out.println("Parámetros: [" + correo + ", " + claveEncriptada + "]");
            
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, claveEncriptada);
            
            System.out.println("4. Ejecutando consulta...");
            rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ LOGIN EXITOSO!");
                System.out.println("Datos del usuario:");
                System.out.println("- ID: " + rs.getInt("id_usuario"));
                System.out.println("- Nombre: " + rs.getString("nombre"));
                System.out.println("- Apellido: " + rs.getString("apellido"));
                System.out.println("- Email: " + rs.getString("correo"));
                System.out.println("- Teléfono: " + rs.getString("telefono"));
                System.out.println("- Estado: " + rs.getInt("estado"));
            } else {
                System.out.println("✗ LOGIN FALLIDO - Usuario no encontrado o credenciales incorrectas");
                
                // Verificar si el usuario existe
                System.out.println("5. Verificando si el usuario existe...");
                String sqlVerificar = "SELECT correo, estado FROM usuarios WHERE correo = ?";
                PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
                psVerificar.setString(1, correo);
                ResultSet rsVerificar = psVerificar.executeQuery();
                
                if (rsVerificar.next()) {
                    System.out.println("- Usuario existe con email: " + rsVerificar.getString("correo"));
                    System.out.println("- Estado del usuario: " + rsVerificar.getInt("estado"));
                    System.out.println("- Problema posible: Contraseña incorrecta o usuario deshabilitado");
                } else {
                    System.out.println("- Usuario NO existe en la base de datos");
                }
                
                rsVerificar.close();
                psVerificar.close();
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR SQL: " + e.getMessage());
            System.err.println("Código de error: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR GENERAL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
                System.out.println("✓ Recursos de BD liberados correctamente");
            } catch (SQLException e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método para encriptar contraseña con MD5
     */
    private static String encriptarMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error encriptando: " + e.getMessage());
            return null;
        }
    }
}