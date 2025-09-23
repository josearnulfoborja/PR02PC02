package com.mycompany.pr02pc02.models;

import com.mycompany.pr02pc02.utils.Conexion;
<<<<<<< HEAD
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
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase modelo Usuario para el sistema de reservas de hotel
 * Contiene todos los atributos y métodos necesarios para el manejo de usuarios
 */
public class Usuario {
    
    // Atributos de la clase Usuario
    private int idUsuario;
>>>>>>> dev
    private String nombre;
    private String apellido;
    private String clave;
    private String correo;
    private String telefono;
    private String roll;
    private boolean estado;
    private String codigoVerificacion;
    private String modoAutenticacion;
<<<<<<< HEAD
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
=======
    private String fechaCreacion;
    private String direccionIP;
    
    // Constructor vacío
    public Usuario() {
    }
    
    // Constructor con parámetros principales
    public Usuario(String nombre, String apellido, String correo, String telefono, String clave) {
>>>>>>> dev
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
<<<<<<< HEAD
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
    
=======
        this.estado = true;
        this.roll = "CLIENTE";
        this.modoAutenticacion = "EMAIL";
    }
    
    // Getters y Setters
    
    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre != null ? nombre.trim() : null;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido != null ? apellido.trim() : null;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo != null ? correo.trim().toLowerCase() : null;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono != null ? telefono.trim() : null;
    }

    /**
     * @return the roll
     */
    public String getRoll() {
        return roll;
    }

    /**
     * @param roll the roll to set
     */
    public void setRoll(String roll) {
        this.roll = roll;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the codigoVerificacion
     */
    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    /**
     * @param codigoVerificacion the codigoVerificacion to set
     */
    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    /**
     * @return the modoAutenticacion
     */
    public String getModoAutenticacion() {
        return modoAutenticacion;
    }

    /**
     * @param modoAutenticacion the modoAutenticacion to set
     */
    public void setModoAutenticacion(String modoAutenticacion) {
        this.modoAutenticacion = modoAutenticacion;
    }

    /**
     * @return the fechaCreacion
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the direccionIP
     */
    public String getDireccionIP() {
        return direccionIP;
    }

    /**
     * @param direccionIP the direccionIP to set
     */
>>>>>>> dev
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }
    
    /**
<<<<<<< HEAD
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
=======
     * Método principal para registrar un usuario en la base de datos
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarUsuario() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean registroExitoso = false;
        
        try {
            // Obtener conexión a la base de datos
            conn = Conexion.getConnection();
            
            // Verificar si el correo ya existe
            if (existeCorreo(conn)) {
                System.out.println("Error: El correo electrónico ya está registrado");
                return false;
            }
            
            // Query para insertar nuevo usuario
            String sql = "INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, " +
                        "roll, estado, codigo_verificacion, modo_autenticacion, fecha_creacion, direccion_ip) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            
            // Establecer parámetros
            pstmt.setString(1, this.nombre);
            pstmt.setString(2, this.apellido);
            pstmt.setString(3, this.clave);
            pstmt.setString(4, this.correo);
            pstmt.setString(5, this.telefono);
            pstmt.setString(6, this.roll);
            pstmt.setBoolean(7, this.estado);
            pstmt.setString(8, this.codigoVerificacion);
            pstmt.setString(9, this.modoAutenticacion);
            pstmt.setString(10, this.fechaCreacion);
            pstmt.setString(11, this.direccionIP);
            
            // Ejecutar la inserción
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                registroExitoso = true;
                System.out.println("Usuario registrado exitosamente: " + this.nombre + " " + this.apellido);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
>>>>>>> dev
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
<<<<<<< HEAD
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
=======
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
>>>>>>> dev
            }
        }
        
        return registroExitoso;
    }
    
    /**
<<<<<<< HEAD
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
=======
     * Verifica si ya existe un usuario con el correo electrónico dado
     * @param conn Conexión a la base de datos
     * @return true si el correo ya existe, false en caso contrario
     */
    private boolean existeCorreo(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.correo);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar correo existente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Método para autenticar un usuario
     * @param correo Correo electrónico del usuario
     * @param clave Contraseña del usuario (ya encriptada en MD5)
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    public static Usuario autenticarUsuario(String correo, String clave) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        
        try {
            conn = Conexion.getConnection();
            
            String sql = "SELECT * FROM usuarios WHERE correo = ? AND clave = ? AND estado = true";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, correo.trim().toLowerCase());
            pstmt.setString(2, clave);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRoll(rs.getString("roll"));
                usuario.setEstado(rs.getBoolean("estado"));
                usuario.setCodigoVerificacion(rs.getString("codigo_verificacion"));
                usuario.setModoAutenticacion(rs.getString("modo_autenticacion"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                usuario.setDireccionIP(rs.getString("direccion_ip"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return usuario;
    }
    
    /**
     * Obtiene el nombre completo del usuario
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    /**
     * Verifica si el usuario es administrador
     * @return true si es administrador, false en caso contrario
     */
    public boolean esAdministrador() {
        return "ADMIN".equalsIgnoreCase(this.roll);
    }
    
    /**
     * Método toString para representación del objeto
     * @return Representación en cadena del usuario
>>>>>>> dev
     */
    @Override
    public String toString() {
        return "Usuario{" +
<<<<<<< HEAD
                "nombre='" + nombre + '\'' +
=======
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
>>>>>>> dev
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", roll='" + roll + '\'' +
                ", estado=" + estado +
<<<<<<< HEAD
                ", fechaCreacion=" + fechaCreacion +
=======
                ", fechaCreacion='" + fechaCreacion + '\'' +
>>>>>>> dev
                ", direccionIP='" + direccionIP + '\'' +
                '}';
    }
}