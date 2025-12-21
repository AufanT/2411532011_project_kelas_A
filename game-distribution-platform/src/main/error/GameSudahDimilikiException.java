package main.error;

public class GameSudahDimilikiException extends Exception {
    public GameSudahDimilikiException(String judulGame) {
        super("Anda sudah memiliki game " + judulGame + " di Library!");
    }
}