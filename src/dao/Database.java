package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/app_scontri";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Méthode pour établir la connexion
    public static Connection getConnection() {
        if (connection == null) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("Erreur : Driver JDBC MySQL non trouvé !");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Erreur : Impossible de se connecter à la base de données !");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Méthode pour fermer la connexion
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connexion fermée !");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion !");
                e.printStackTrace();
            }
        }
    }
}
