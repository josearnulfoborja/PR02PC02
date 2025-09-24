package com.mycompany.parcial2.modelo;

import com.mycompany.parcial2.conexion.Conexion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Usuario {
    private String nombre;
    private String apellido;
    private String clave;
    private String correo;
    private String telefono;
    private String roll;
    private String estado;
    private String codigoVerificacion;
    private String modoAutenticacion;
    private Timestamp fechaCreacion;
    private String direccionIP;
    
    public Usuario() {
        this.roll = "usuario";
        this.estado = "Inactivo";
        this.modoAutenticacion = "Autoregistro";
        this.fechaCreacion = new Timestamp(System.currentTimeMillis());
    }
    
    public Usuario(String nombre, String apellido, String clave, String correo, String telefono) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = capitalizarCadaPalabra(nombre);
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = capitalizarCadaPalabra(apellido);
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
        this.correo = correo != null ? correo.trim().toLowerCase() : null;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono != null ? telefono.trim() : null;
    }
    
    public String getRoll() {
        return roll;
    }
    
    public void setRoll(String roll) {
        this.roll = roll;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
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
    
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getDireccionIP() {
        return direccionIP;
    }
    
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }
    
    private static String capitalizarCadaPalabra(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }
        
        String textoLimpio = texto.trim();
        String[] palabras = textoLimpio.split("\\s+");
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (palabra.length() > 0) {
                String palabraFormateada = palabra.substring(0, 1).toUpperCase() + 
                                         palabra.substring(1).toLowerCase();
                resultado.append(palabraFormateada);
            }
            
            if (i < palabras.length - 1) {
                resultado.append(" ");
            }
        }
        
        return resultado.toString();
    }
    
    public static String encriptarMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al encriptar contraseña: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean correoExiste(String correo) {
        Connection connection = null;
        PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        boolean existe = false;
        
        try {
            connection = Conexion.getConnection();
            
            String sql = "SELECT COUNT(*) as total FROM usuarios WHERE correo = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, correo.toLowerCase().trim());
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                existe = total > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar correo existente: " + e.getMessage());
            e.printStackTrace();
            existe = true;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Conexion.closeConnection(connection);
        }
        
        return existe;
    }
    
    public boolean registrarUsuario() {
        // Verificar si el correo ya existe
        if (correoExiste(this.correo)) {
            System.err.println("Error: El correo ya está registrado: " + this.correo);
            return false;
        }
        
        Connection connection = null;
        PreparedStatement statement = null;
        boolean registroExitoso = false;
        
        try {
            connection = Conexion.getConnection();
            
            String sql = "INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, " +
                        "roll, estado, codigoVerificacion, modoAutenticacion, fechaCreacion, direccionIP) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, this.nombre);
            statement.setString(2, this.apellido);
            statement.setString(3, this.clave);
            statement.setString(4, this.correo);
            statement.setString(5, this.telefono);
            statement.setString(6, this.roll);
            statement.setString(7, this.estado);
            statement.setString(8, this.codigoVerificacion);
            statement.setString(9, this.modoAutenticacion);
            statement.setTimestamp(10, this.fechaCreacion);
            statement.setString(11, this.direccionIP);
            
            int filasAfectadas = statement.executeUpdate();
            registroExitoso = filasAfectadas > 0;
            
            if (registroExitoso) {
                System.out.println("Usuario registrado exitosamente: " + this.correo);
            } else {
                System.err.println("No se pudo registrar el usuario: " + this.correo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            registroExitoso = false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Conexion.closeConnection(connection);
        }
        
        return registroExitoso;
    }
    
    public String generarCodigoVerificacion() {
        return String.valueOf(System.currentTimeMillis()) + 
               String.valueOf((int)(Math.random() * 1000));
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", roll='" + roll + '\'' +
                ", estado='" + estado + '\'' +
                ", direccionIP='" + direccionIP + '\'' +
                '}';
    }
}