package src.confg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/laundry_apps";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection koneksi() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    connection.setAutoCommit(true);
                } catch (ClassNotFoundException e) {
                    return null;
                } catch (SQLException e) {
                    return null;
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return connection;
    }
}