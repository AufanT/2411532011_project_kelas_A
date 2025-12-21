package main.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.Game;

public class LibraryDAO extends BaseDAO {

    public boolean isGameOwned(String userId, String gameId) {
        String query = "SELECT * FROM library WHERE user_id = ? AND game_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void addToLibrary(String userId, String gameId) {
        String query = "INSERT INTO library (user_id, game_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Game> getUserLibrary(String userId) {
        List<Game> list = new ArrayList<>();
        String query = "SELECT g.* FROM games g JOIN library l ON g.id = l.game_id WHERE l.user_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Game.Builder()
                        .setId(rs.getString("id"))
                        .setTitle(rs.getString("title"))
                        .setGenre(rs.getString("genre"))
                        .setPrice(rs.getDouble("price"))
                        .setImagePath(rs.getString("image_path"))
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // --- SECTION: WISHLIST ---

    public boolean isGameInWishlist(String userId, String gameId) {
        String query = "SELECT * FROM wishlist WHERE user_id = ? AND game_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void addToWishlist(String userId, String gameId) {
        String query = "INSERT INTO wishlist (user_id, game_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void removeFromWishlist(String userId, String gameId) {
        String query = "DELETE FROM wishlist WHERE user_id = ? AND game_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Game> getUserWishlist(String userId) {
        List<Game> list = new ArrayList<>();
        String query = "SELECT g.* FROM games g JOIN wishlist w ON g.id = w.game_id WHERE w.user_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Game.Builder()
                        .setId(rs.getString("id"))
                        .setTitle(rs.getString("title"))
                        .setGenre(rs.getString("genre"))
                        .setPrice(rs.getDouble("price"))
                        .setImagePath(rs.getString("image_path"))
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}