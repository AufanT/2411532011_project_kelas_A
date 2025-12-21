package main.error;

public class GameSudahDiWishlistException extends Exception {
    public GameSudahDiWishlistException(String judul) {
        super("Game '" + judul + "' sudah ada di Wishlist kamu!");
    }
}