package main.service;

import java.util.List;
import main.DAO.GameDAO;
import main.DAO.LibraryDAO;
import main.DAO.UserDAO;
import main.error.RegisterFailedException;
import main.model.*;

public class AppService {
    private static AppService instance;
    private User currentUser;

    private final GameDAO gameDAO = new GameDAO();
    private final UserDAO userDAO = new UserDAO();
    private final LibraryDAO libraryDAO = new LibraryDAO();

    private AppService() {}

    // Singleton Pattern
    public static AppService getInstance() {
        if (instance == null) instance = new AppService();
        return instance;
    }

    // SECTION: AUTHENTICATION

    public boolean login(String username, String password) {
        currentUser = userDAO.authenticate(username, password);
        return currentUser != null;
    }

    public void logout() { 
        currentUser = null; 
    }

    public User getCurrentUser() { 
        return currentUser; 
    }

    public void registerUser(String username, String password, String confirmPassword) throws Exception {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new RegisterFailedException("Username dan Password tidak boleh kosong!");
        }
        if (!password.equals(confirmPassword)) {
            throw new RegisterFailedException("Konfirmasi password tidak sesuai!");
        }
        if (userDAO.existsByUsername(username)) {
            throw new RegisterFailedException("Username '" + username + "' sudah digunakan!");
        }

        String id = "U" + ((int)(Math.random() * 9000) + 1000);
        userDAO.save(id, username, password);
    }

    // SECTION: GAME CRUD

    public List<Game> getGames() { 
        return gameDAO.findAll(); 
    }

    public void addGame(Game g) { 
        gameDAO.save(g); 
    }

    public void updateGame(Game g) { 
        gameDAO.update(g); 
    }

    public void deleteGame(Game g) { 
        gameDAO.delete(g.getId()); 
    }

    public boolean isGameTitleExists(String title) { 
        return gameDAO.existsByTitle(title); 
    }

    // SECTION: USER DATA & LIBRARY

    public void updateUserBalance(RegularUser user) {
        userDAO.updateBalance(user.getId(), user.getBalance());
    }

    public boolean isGameOwned(String userId, String gameId) {
        return libraryDAO.isGameOwned(userId, gameId);
    }

    public void addToLibrary(String userId, String gameId) {
        libraryDAO.addToLibrary(userId, gameId);
    }

    public List<Game> getUserLibrary(String userId) {
        return libraryDAO.getUserLibrary(userId);
    }

    // SECTION: WISHLIST

    public boolean isGameInWishlist(String userId, String gameId) {
        return libraryDAO.isGameInWishlist(userId, gameId);
    }

    public void addToWishlist(String userId, String gameId) {
        libraryDAO.addToWishlist(userId, gameId);
    }

    public void removeFromWishlist(String userId, String gameId) {
        libraryDAO.removeFromWishlist(userId, gameId);
    }

    public List<Game> getUserWishlist(String userId) {
        return libraryDAO.getUserWishlist(userId);
    }
}