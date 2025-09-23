package com.mycompany.pr02pc02.models;
import com.mycompany.pr02pc02.utils.Conexion;
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
    private String nombre;
    private String apellido;
    private String clave;
    private String correo;
    private String telefono;
    private String roll;
    private boolean estado;
    private String codigoVerificacion;
    private String modoAutenticacion;
    private String fechaCreacion;
    private String direccionIP;
    
    // Constructor vacío
    public Usuario() {
    }
    
    // Constructor con parámetros principales
    public Usuario(String nombre, String apellido, String correo, String telefono, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
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
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }
    
    /**
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
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        
        return registroExitoso;
    }
    
    /**
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
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", roll='" + roll + '\'' +
                ", estado=" + estado +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", direccionIP='" + direccionIP + '\'' +
                '}';
    }
}