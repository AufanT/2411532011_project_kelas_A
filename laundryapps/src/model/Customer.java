package src.model;

public class Customer {
    private String id, nama, email, alamat, telepon;

    public Customer(String id, String nama, String email, String alamat, String telepon) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelepon() {
        return telepon;
    }
    
}
