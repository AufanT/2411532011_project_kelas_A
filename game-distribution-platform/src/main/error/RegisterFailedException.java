package main.error;

public class RegisterFailedException extends Exception {
    public RegisterFailedException(String message) {
        super(message);
    }
}