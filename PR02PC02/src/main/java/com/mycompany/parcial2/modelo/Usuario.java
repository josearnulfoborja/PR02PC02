package com.mycompany.parcial2.modelo;

import com.mycompany.parcial2.conexion.Conexion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Clase modelo Usuario
 * Representa un usuario del sistema con todos sus atributos y métodos
 */
public class Usuario {
    
    // Atributos del usuario
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
    
    // Constructor vacío
    public Usuario() {
        this.roll = "usuario";
        this.estado = "Inactivo";
        this.modoAutenticacion = "Autoregistro";
        this.fechaCreacion = new Timestamp(System.currentTimeMillis());
    }
    
    // Constructor con parámetros principales
    public Usuario(String nombre, String apellido, String clave, String correo, String telefono) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    // Getters y Setters
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
    
    /**
     * Método auxiliar para formatear texto con primera letra en mayúscula para cada palabra
     * @param texto texto a formatear
     * @return texto con primera letra de cada palabra en mayúscula y el resto en minúscula
     */
    private static String capitalizarCadaPalabra(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }
        
        String textoLimpio = texto.trim();
        String[] palabras = textoLimpio.split("\\s+"); // Dividir por uno o más espacios
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (palabra.length() > 0) {
                // Capitalizar primera letra y el resto en minúscula
                String palabraFormateada = palabra.substring(0, 1).toUpperCase() + 
                                         palabra.substring(1).toLowerCase();
                resultado.append(palabraFormateada);
            }
            
            // Agregar espacio entre palabras (excepto en la última)
            if (i < palabras.length - 1) {
                resultado.append(" ");
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Encripta una contraseña usando MD5
     * @param password contraseña a encriptar
     * @return contraseña encriptada en MD5
     */
    public static String encriptarMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            
            // Convertir byte array a hexadecimal
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
    
    /**
     * Verifica si un correo electrónico ya existe en la base de datos
     * @param correo correo electrónico a verificar
     * @return true si el correo ya existe, false si está disponible
     */
    public static boolean correoExiste(String correo) {
        Connection connection = null;
        PreparedStatement statement = null;
        java.sql.ResultSet resultSet = null;
        boolean existe = false;
        
        try {
            // Obtener conexión
            connection = Conexion.getConnection();
            
            // Query de búsqueda
            String sql = "SELECT COUNT(*) as total FROM usuarios WHERE correo = ?";
            
            // Preparar statement
            statement = connection.prepareStatement(sql);
            statement.setString(1, correo.toLowerCase().trim());
            
            // Ejecutar consulta
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                existe = total > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar correo existente: " + e.getMessage());
            e.printStackTrace();
            // En caso de error, asumimos que existe para prevenir duplicados
            existe = true;
        } finally {
            // Cerrar recursos
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
    
    /**
     * Registra el usuario en la base de datos
     * @return true si el registro fue exitoso, false en caso contrario
     */
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
            // Obtener conexión
            connection = Conexion.getConnection();
            
            // Query de inserción
            String sql = "INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, " +
                        "roll, estado, codigoVerificacion, modoAutenticacion, fechaCreacion, direccionIP) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Preparar statement
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
            
            // Ejecutar inserción
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
            // Cerrar recursos
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
    
    /**
     * Genera un código de verificación aleatorio
     * @return código de verificación
     */
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