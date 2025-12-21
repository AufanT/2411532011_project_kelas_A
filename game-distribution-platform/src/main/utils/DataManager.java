package main.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.*;

public class DataManager {
    private static DataManager instance;
    private User currentUser;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }

    // --- LOGIN DENGAN DATABASE ---
    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String role = rs.getString("role");
                double balance = rs.getDouble("balance"); 

                currentUser = UserFactory.createUser(role, id, username, password);

                if (currentUser instanceof RegularUser) {
                    ((RegularUser) currentUser).setBalance(balance);
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }

    // --- CRUD GAMES (READ) ---
    public List<Game> getGames() {
        List<Game> list = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            
            while (rs.next()) {
                Game g = new Game.Builder()
                        .setId(rs.getString("id"))
                        .setTitle(rs.getString("title"))
                        .setGenre(rs.getString("genre"))
                        .setPrice(rs.getDouble("price"))
                        .setImagePath(rs.getString("image_path"))
                        .build();
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- CRUD GAMES (CREATE) ---
    public void addGame(Game g) {
        String query = "INSERT INTO games (id, title, genre, price, image_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, g.getId());
            st.setString(2, g.getTitle());
            st.setString(3, g.getGenre());
            st.setDouble(4, g.getPrice());
            st.setString(5, g.getImagePath());
            st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- CRUD GAMES (UPDATE) ---
    public void updateGame(Game g) {
        String query = "UPDATE games SET title=?, genre=?, price=?, image_path=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, g.getTitle());
            st.setString(2, g.getGenre());
            st.setDouble(3, g.getPrice());
            st.setString(4, g.getImagePath());
            st.setString(5, g.getId()); 
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- CRUD GAMES (DELETE) ---
    public void deleteGame(Game g) {
        String query = "DELETE FROM games WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, g.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- UPDATE SALDO USER (PENTING BUAT BELI GAME) ---
    public void updateUserBalance(RegularUser user) {
        String query = "UPDATE users SET balance=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setDouble(1, user.getBalance());
            st.setString(2, user.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 1. Cek apakah user sudah punya game ini?
    public boolean isGameOwned(String userId, String gameId) {
        String query = "SELECT * FROM library WHERE user_id = ? AND game_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            ResultSet rs = st.executeQuery();
            return rs.next(); // True jika data ditemukan
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Simpan Transaksi ke Database (Masuk Library)
    public void addToLibrary(String userId, String gameId) {
        String query = "INSERT INTO library (user_id, game_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Ambil Daftar Game Milik User (Untuk Tab Library)
    public List<Game> getUserLibrary(String userId) {
        List<Game> list = new ArrayList<>();
        // Join tabel games dan library
        String query = "SELECT g.* FROM games g JOIN library l ON g.id = l.game_id WHERE l.user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Game g = new Game.Builder()
                        .setId(rs.getString("id"))
                        .setTitle(rs.getString("title"))
                        .setGenre(rs.getString("genre"))
                        .setPrice(rs.getDouble("price"))
                        .setImagePath(rs.getString("image_path"))
                        .build();
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // --- CEK DUPLIKAT JUDUL GAME ---
    public boolean isGameTitleExists(String title) {
        // Query cek judul (Case Insensitive)
        String query = "SELECT id FROM games WHERE LOWER(title) = LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            
            // Jika rs.next() true, berarti ada data (judul sudah terpakai)
            return rs.next(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // --- WISHLIST FEATURES ---

    // 1. Cek apakah game ada di wishlist?
    public boolean isGameInWishlist(String userId, String gameId) {
        String query = "SELECT * FROM wishlist WHERE user_id = ? AND game_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 2. Tambah ke Wishlist
    public void addToWishlist(String userId, String gameId) {
        String query = "INSERT INTO wishlist (user_id, game_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 3. Hapus dari Wishlist
    public void removeFromWishlist(String userId, String gameId) {
        String query = "DELETE FROM wishlist WHERE user_id = ? AND game_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            st.setString(2, gameId);
            st.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 4. Ambil semua game di Wishlist User
    public List<Game> getUserWishlist(String userId) {
        List<Game> list = new ArrayList<>();
        String query = "SELECT g.* FROM games g JOIN wishlist w ON g.id = w.game_id WHERE w.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Game g = new Game.Builder()
                        .setId(rs.getString("id"))
                        .setTitle(rs.getString("title"))
                        .setGenre(rs.getString("genre"))
                        .setPrice(rs.getDouble("price"))
                        .setImagePath(rs.getString("image_path"))
                        .build();
                list.add(g);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    // --- FITUR REGISTRASI ---

    // 1. Cek Username Kembar
    public boolean isUsernameExists(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, username);
            return st.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // 2. Register User Baru
    // --- UPDATE FITUR REGISTRASI (FULL VALIDATION) ---

    public void registerUser(String username, String password, String confirmPassword) throws Exception {
        
        // 1. Validasi Input Kosong
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            throw new main.error.RegisterFailedException("Username dan Password tidak boleh kosong!");
        }

        // 2. Validasi Konfirmasi Password
        if (!password.equals(confirmPassword)) {
            throw new main.error.RegisterFailedException("Konfirmasi password tidak sesuai!");
        }

        // 3. Validasi Format Username (Hanya Huruf, Angka, Underscore. Tidak boleh Spasi)
        // Regex: ^[a-zA-Z0-9_]+$ artinya start sampai end hanya boleh a-z, A-Z, 0-9, atau _
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new main.error.RegisterFailedException("Username hanya boleh huruf, angka, dan underscore (_). Tidak boleh ada spasi!");
        }

        // 4. Validasi Panjang Password (Minimal 5 Karakter)
        if (password.length() < 5) {
            throw new main.error.RegisterFailedException("Password terlalu pendek! Minimal 5 karakter.");
        }

        // 5. Validasi Duplikasi di Database
        if (isUsernameExists(username)) {
            throw new main.error.RegisterFailedException("Username '" + username + "' sudah digunakan orang lain!");
        }

        // 6. Proses Simpan ke Database
        int rand = (int)(Math.random() * 9000) + 1000;
        String id = "U" + rand;

        String query = "INSERT INTO users (id, username, password, role, balance) VALUES (?, ?, ?, 'USER', 0)";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement st = conn.prepareStatement(query)) {
            
            st.setString(1, id);
            st.setString(2, username);
            st.setString(3, password);
            st.executeUpdate();

        } catch (SQLException e) {
            // Tangkap Error Database (Misal koneksi putus)
            e.printStackTrace();
            throw new main.error.RegisterFailedException("Gagal terhubung ke database: " + e.getMessage());
        }
    }
}