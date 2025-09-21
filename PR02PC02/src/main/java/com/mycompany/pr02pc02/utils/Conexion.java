package com.mycompany.pr02pc02.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión a la base de datos MySQL
 * Proporciona métodos estáticos para obtener conexiones a la base de datos 'plataforma'
 */
public class Conexion {
    
    // Parámetros de conexión a MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/plataforma?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";
    
    // Variables para el pool de conexiones básico
    private static Connection conexion = null;
    
    /**
     * Constructor privado para evitar instanciación
     */
    private Conexion() {
    }
    
    /**
     * Método principal para obtener una conexión a la base de datos
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            
            // Verificar que la conexión sea válida
            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexión establecida exitosamente a la base de datos 'plataforma'");
                return conexion;
            } else {
                throw new SQLException("No se pudo establecer la conexión a la base de datos");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL");
            System.err.println("Asegúrese de tener mysql-connector-java en el classpath");
            throw new SQLException("Driver de MySQL no encontrado", e);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            System.err.println("Verifique que:");
            System.err.println("1. MySQL esté ejecutándose en localhost:3306");
            System.err.println("2. La base de datos 'plataforma' exista");
            System.err.println("3. Las credenciales sean correctas");
            throw e;
        }
    }
    
    /**
     * Método alternativo con parámetros personalizados
     * @param host Servidor de base de datos
     * @param puerto Puerto de conexión
     * @param baseDatos Nombre de la base de datos
     * @param usuario Usuario de la base de datos
     * @param password Contraseña del usuario
     * @return Connection objeto de conexión
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConnection(String host, String puerto, String baseDatos, 
                                         String usuario, String password) throws SQLException {
        try {
            Class.forName(DRIVER);
            
            String urlPersonalizada = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDatos + 
                                    "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            
            Connection conn = DriverManager.getConnection(urlPersonalizada, usuario, password);
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexión personalizada establecida a: " + baseDatos);
                return conn;
            } else {
                throw new SQLException("No se pudo establecer la conexión personalizada");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de MySQL no encontrado");
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
    }
    
    /**
     * Método para cerrar una conexión
     * @param conn Conexión a cerrar
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexión cerrada exitosamente");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método para probar la conexión
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error en prueba de conexión: " + e.getMessage());
            return false;
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     * Método para obtener información de la conexión actual
     * @return String con información de la base de datos
     */
    public static String getConnectionInfo() {
        Connection conn = null;
        StringBuilder info = new StringBuilder();
        
        try {
            conn = getConnection();
            if (conn != null) {
                info.append("=== INFORMACIÓN DE LA CONEXIÓN ===\n");
                info.append("URL: ").append(conn.getMetaData().getURL()).append("\n");
                info.append("Usuario: ").append(conn.getMetaData().getUserName()).append("\n");
                info.append("Driver: ").append(conn.getMetaData().getDriverName()).append("\n");
                info.append("Versión del Driver: ").append(conn.getMetaData().getDriverVersion()).append("\n");
                info.append("Base de datos: ").append(conn.getMetaData().getDatabaseProductName()).append("\n");
                info.append("Versión de la BD: ").append(conn.getMetaData().getDatabaseProductVersion()).append("\n");
                info.append("================================");
            }
        } catch (SQLException e) {
            info.append("Error al obtener información de conexión: ").append(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        
        return info.toString();
    }
    
    /**
     * Método main para pruebas de conexión
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN A BASE DE DATOS ===");
        
        if (testConnection()) {
            System.out.println("✓ Conexión exitosa");
            System.out.println(getConnectionInfo());
        } else {
            System.out.println("✗ Error de conexión");
            System.out.println("Instrucciones para solucionar:");
            System.out.println("1. Instalar MySQL Server");
            System.out.println("2. Crear la base de datos 'plataforma'");
            System.out.println("3. Ejecutar el script SQL para crear las tablas");
            System.out.println("4. Verificar usuario/contraseña en esta clase");
        }
    }
}