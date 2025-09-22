package com.mycompany.pr02pc02.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    
    // Datos de conexión: AJÚSTALOS según tu entorno
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // tu usuario MySQL
    private static final String PASSWORD = "root";   // tu contraseña MySQL
    
    // Método para obtener la conexión
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Cargar el driver de MySQL (opcional desde JDBC 4.0, pero recomendable)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("| Conexión establecida con la base de datos |");
        } catch (ClassNotFoundException e) {
            System.out.println("X Error: No se encontró el driver JDBC de MySQL X");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("X Error al conectar con la base de datos X");
            e.printStackTrace();
        }
        return conn;
    }
}
