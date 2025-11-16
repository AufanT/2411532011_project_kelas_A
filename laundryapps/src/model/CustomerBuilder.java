package src.model;

public class CustomerBuilder {
    private String id, nama, alamat, telepon;
    private String email = "";

    public CustomerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setAlamat(String alamat) {
        this.alamat = alamat;
        return this;
    }


    public CustomerBuilder setTelepon(String telepon) {
        this.telepon = telepon;
        return this;
    }

    public Customer build() {
        return new Customer(id, nama, email, alamat, telepon);
    }
}
