package com.mycompany.pr02pc02.models;

import com.mycompany.pr02pc02.utils.Conexion;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Clase modelo Usuario para el sistema de reservas de hotel
 * Representa la entidad usuario con todos sus atributos y métodos de negocio
 */
public class Usuario {
    
    // Atributos del usuario según especificaciones
    private String nombre;
    private String apellido;
    private String clave;
    private String correo;
    private String telefono;
    private String roll;
    private boolean estado;
    private String codigoVerificacion;
    private String modoAutenticacion;
    private LocalDateTime fechaCreacion;
    private String direccionIP;
    
    /**
     * Constructor vacío
     */
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = true;
        this.roll = "CLIENTE"; // Rol por defecto
        this.modoAutenticacion = "LOCAL";
    }
    
    /**
     * Constructor con parámetros principales
     */
    public Usuario(String nombre, String apellido, String correo, String telefono, String clave) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
    }
    
    // GETTERS Y SETTERS
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getRoll() {
        return roll;
    }
    
    public void setRoll(String roll) {
        this.roll = roll;
    }
    
    public boolean isEstado() {
        return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }
    
    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }
    
    public String getModoAutenticacion() {
        return modoAutenticacion;
    }
    
    public void setModoAutenticacion(String modoAutenticacion) {
        this.modoAutenticacion = modoAutenticacion;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getDireccionIP() {
        return direccionIP;
    }
    
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }
    
    /**
     * Método para encriptar contraseña usando MD5
     * @param texto Texto a encriptar
     * @return String encriptado en MD5
     */
    public static String encriptarMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error encriptando contraseña: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Método principal para registrar usuario en la base de datos
     * @return true si el registro fue exitoso, false si hubo error
     */
    public boolean registrarUsuario() {
        Connection con = null;
        PreparedStatement ps = null;
        boolean registroExitoso = false;
        
        try {
            // Obtener conexión
            con = Conexion.getConnection();
            
            if (con == null) {
                System.err.println("No se pudo establecer conexión con la base de datos");
                return false;
            }
            
            // Encriptar contraseña
            String claveEncriptada = encriptarMD5(this.clave);
            if (claveEncriptada == null) {
                System.err.println("Error encriptando la contraseña");
                return false;
            }
            
            // Preparar consulta SQL
            String sql = "INSERT INTO usuarios (nombre, apellido, correo, telefono, clave, roll, estado, " +
                        "codigo_verificacion, modo_autenticacion, fecha_creacion, direccion_ip) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setString(2, this.apellido);
            ps.setString(3, this.correo);
            ps.setString(4, this.telefono);
            ps.setString(5, claveEncriptada);
            ps.setString(6, this.roll);
            ps.setBoolean(7, this.estado);
            ps.setString(8, this.codigoVerificacion);
            ps.setString(9, this.modoAutenticacion);
            ps.setTimestamp(10, Timestamp.valueOf(this.fechaCreacion));
            ps.setString(11, this.direccionIP);
            
            // Ejecutar inserción
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Usuario registrado exitosamente: " + this.correo);
                registroExitoso = true;
            } else {
                System.err.println("No se pudo insertar el usuario");
            }
            
        } catch (SQLException e) {
            System.err.println("Error SQL al registrar usuario: " + e.getMessage());
            
            // Verificar si es error de duplicado
            if (e.getErrorCode() == 1062 || e.getMessage().contains("Duplicate entry")) {
                System.err.println("El correo electrónico ya está registrado");
            }
            
        } catch (Exception e) {
            System.err.println("Error general al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        
        return registroExitoso;
    }
    
    /**
     * Método para generar código de verificación aleatorio
     * @return Código de verificación de 6 dígitos
     */
    public String generarCodigoVerificacion() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
    
    /**
     * Método para validar formato de email
     * @param email Email a validar
     * @return true si el formato es válido
     */
    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
    
    /**
     * Método para validar teléfono
     * @param telefono Teléfono a validar
     * @return true si el formato es válido
     */
    public static boolean validarTelefono(String telefono) {
        return telefono != null && telefono.replaceAll("[\\s\\-\\+\\(\\)]", "").matches("^[0-9]{8,15}$");
    }
    
    /**
     * Método toString para debugging
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", roll='" + roll + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", direccionIP='" + direccionIP + '\'' +
                '}';
    }
}