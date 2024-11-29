package Conexion;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Cargar las variables de entorno desde el archivo .env
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String username = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    // Patrón Singleton para manejar la conexión de manera eficiente
    private static Connection connection = null;

    // Método para obtener la conexión a la base de datos
    public static Connection conectar() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Conectado exitosamente");
            } catch (SQLException e) {
                System.out.println("No se puede conectar a la base de datos.");
                System.out.println("Error: " + e.getMessage());
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    // Método main para probar la conexión
    public static void main(String[] args) {
        Connection connection = Conexion.conectar();
        if (connection != null) {
            // Aquí podrías agregar alguna lógica adicional si es necesario
            // Ejemplo: Realizar alguna consulta a la base de datos

            // Finalmente, cerramos la conexión después de usarla
            Conexion.cerrarConexion();
        }
    }
}
