package src.util;

import src.error.ValidationException;
import src.model.User;  

public class ValidationUtil {
    public static void validateUser(User user) throws ValidationException, NullPointerException {
        if (user.getUsername() == null) {
            throw new NullPointerException("Username is null");
        } else if (user.getUsername().isBlank()) {
            throw new ValidationException("Username is blank");
        } else if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        } else if (user.getPassword().isBlank()) {
            throw new ValidationException("Password is blank");
        }
    }
}
