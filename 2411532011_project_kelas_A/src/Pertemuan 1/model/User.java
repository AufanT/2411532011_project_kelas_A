package model;

public class User {
    String id, nama, username, password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean login(String username, String password) {
        boolean isLoggin = false;

        User user = new User();
        user.setId("1");
        user.setNama("fulan");
        user.setUsername("fulan123");
        user.setPassword("password");

        if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
            isLoggin = true;
        } else {
            isLoggin = false;
        }
        return isLoggin;

    }

}
