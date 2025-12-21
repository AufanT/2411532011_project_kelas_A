package main.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.Game;

public class GameDAO extends BaseDAO {

    // READ: Mengambil semua data game 
    public List<Game> findAll() {
        List<Game> list = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query)) {
            
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

    // CREATE: Menambah game baru 
    public void save(Game g) {
        String query = "INSERT INTO games (id, title, genre, price, image_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, g.getId());
            st.setString(2, g.getTitle());
            st.setString(3, g.getGenre());
            st.setDouble(4, g.getPrice());
            st.setString(5, g.getImagePath());
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // UPDATE: Memperbarui data game 
    public void update(Game g) {
        String query = "UPDATE games SET title=?, genre=?, price=?, image_path=? WHERE id=?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, g.getTitle());
            st.setString(2, g.getGenre());
            st.setDouble(3, g.getPrice());
            st.setString(4, g.getImagePath());
            st.setString(5, g.getId());
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // DELETE: Menghapus game 
    public void delete(String id) {
        String query = "DELETE FROM games WHERE id=?";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean existsByTitle(String title) {
        String query = "SELECT id FROM games WHERE LOWER(title) = LOWER(?)";
        try (Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, title);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}