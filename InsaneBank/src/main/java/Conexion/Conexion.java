package Conexion;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String username = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");
    private static final String dbHost = dotenv.get("DB_HOST");
    private static final String url = "jdbc:mysql://" + dbHost + ":3306/insane_bank?useSSL=false&allowPublicKeyRetrieval=true&encrypt=false";

    public static Connection conectar() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado exitosamente");
        } catch (SQLException e) {
            System.out.println("no se puede conectar papu");
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection connection = conectar();
    }
}