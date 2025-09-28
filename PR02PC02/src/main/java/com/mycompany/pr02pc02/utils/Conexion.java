package com.mycompany.pr02pc02.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/plataforma";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        if (connection == null) {
            System.out.println("La conexión es nula. Verifique la configuración de la base de datos.");
        }
        return connection;
    }
}