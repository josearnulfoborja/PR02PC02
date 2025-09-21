package com.mycompany.pr02pc02;

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
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRoll() { return roll; }
    public void setRoll(String roll) { this.roll = roll; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCodigoVerificacion() { return codigoVerificacion; }
    public void setCodigoVerificacion(String codigoVerificacion) { this.codigoVerificacion = codigoVerificacion; }
    public String getModoAutenticacion() { return modoAutenticacion; }
    public void setModoAutenticacion(String modoAutenticacion) { this.modoAutenticacion = modoAutenticacion; }
    public String getDireccionIP() { return direccionIP; }
    public void setDireccionIP(String direccionIP) { this.direccionIP = direccionIP; }

    // Método para registrar usuario en la BD
    public boolean registrarUsuario() {
        String sql = "INSERT INTO usuarios (nombre, apellido, clave, correo, telefono, roll, estado, codigoVerificacion, modoAutenticacion, direccionIP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, clave);
            ps.setString(4, correo);
            ps.setString(5, telefono);
            ps.setString(6, roll);
            ps.setString(7, estado);
            ps.setString(8, codigoVerificacion);
            ps.setString(9, modoAutenticacion);
            ps.setString(10, direccionIP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
