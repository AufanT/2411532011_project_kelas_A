package main.model;

public class AdminUser extends User {
    public AdminUser(String id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}