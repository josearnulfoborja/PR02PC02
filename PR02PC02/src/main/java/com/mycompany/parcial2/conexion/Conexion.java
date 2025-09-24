package com.mycompany.parcial2.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión a la base de datos MySQL
 * Maneja la conexión con la base de datos plataforma
 */
public class Conexion {
    
    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Obtiene una conexión a la base de datos MySQL
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si hay error en la conexión
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            
            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            
            // Configurar la conexión para UTF-8
            connection.createStatement().execute("SET NAMES utf8mb4");
            connection.createStatement().execute("SET CHARACTER SET utf8mb4");
            
            System.out.println("Conexión establecida exitosamente");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de MySQL no encontrado");
            e.printStackTrace();
            throw new SQLException("Driver de MySQL no encontrado", e);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     * @param connection conexión a cerrar
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada exitosamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Método para probar la conexión
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar la conexión: " + e.getMessage());
            return false;
        }
    }
}