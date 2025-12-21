package main.utils;

import main.model.*;

public class UserFactory {

    public static User createUser(String role, String id, String username, String password) {
        if (role == null) {
            return null;
        }
        
        if (role.equalsIgnoreCase("ADMIN")) {
            return new AdminUser(id, username, password);
        } 
        else if (role.equalsIgnoreCase("USER")) {
            return new RegularUser(id, username, password);
        }
        
        return null;
    }
}