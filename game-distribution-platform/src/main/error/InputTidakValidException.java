package main.error;

public class InputTidakValidException extends Exception {
    public InputTidakValidException(String pesan) {
        super(pesan);
    }
}