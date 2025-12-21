package main.DAO;

import java.sql.Connection;
import main.config.DatabaseConnection;

public abstract class BaseDAO {
    protected Connection getConnection() {
        return DatabaseConnection.getConnection();
    }
}