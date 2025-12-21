package main.error;

public class GameSudahAdaException extends Exception {
    public GameSudahAdaException(String judulGame) {
        super("Gagal! Game dengan judul \"" + judulGame + "\" sudah terdaftar di database.");
    }
}