package main.DAO;

import java.sql.*;
import main.model.*;
import main.utils.UserFactory;

public class UserDAO extends BaseDAO {

    public User authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String role = rs.getString("role");
                double balance = rs.getDouble("balance");

                User user = UserFactory.createUser(role, id, username, password);
                if (user instanceof RegularUser) {
                    ((RegularUser) user).setBalance(balance);
                }
                return user;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void save(String id, String username, String password) throws SQLException {
        String query = "INSERT INTO users (id, username, password, role, balance) VALUES (?, ?, ?, 'USER', 0)";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, id);
            st.setString(2, username);
            st.setString(3, password);
            st.executeUpdate();
        }
    }

    public void updateBalance(String userId, double balance) {
        String query = "UPDATE users SET balance=? WHERE id=?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setDouble(1, balance);
            st.setString(2, userId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean existsByUsername(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, username);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}