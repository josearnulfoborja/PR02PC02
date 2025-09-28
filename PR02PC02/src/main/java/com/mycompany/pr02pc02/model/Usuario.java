package com.mycompany.pr02pc02.model;

import com.mycompany.pr02pc02.utils.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
    private String nombre;
    private String apellido;
    private String clave;
    private String correo;
    private String telefono;
    private String roll = "usuario";
    private String estado = "Inactivo";
    private String codigoVerificacion;
    private String modoAutenticacion = "Autoregistro";
    private String direccionIP;

    // Getters y Setters
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

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    // Método para registrar usuario en la base de datos
    public boolean registrarUsuario() {
        String sql = "INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, roll, estado, codigoVerificacion, modoAutenticacion, direccionIP) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection()) {
            if (conn == null) {
                System.out.println("Error: No se pudo establecer la conexión a la base de datos.");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, this.nombre);
                stmt.setString(2, this.apellido);
                stmt.setString(3, this.clave);
                stmt.setString(4, this.correo);
                stmt.setString(5, this.telefono);
                stmt.setString(6, this.roll);
                stmt.setString(7, this.estado);
                stmt.setString(8, this.codigoVerificacion);
                stmt.setString(9, this.modoAutenticacion);
                stmt.setString(10, this.direccionIP);

                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}