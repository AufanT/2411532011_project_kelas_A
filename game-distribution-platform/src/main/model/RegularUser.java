package main.model;

public class RegularUser extends User {
    private double balance;

    public RegularUser(String id, String username, String password) {
        super(id, username, password);
        this.balance = 0.0;
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String getRole() { return "USER"; }
}