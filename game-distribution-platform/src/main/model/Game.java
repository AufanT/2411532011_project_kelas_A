package main.model;

import main.error.SaldoKurangException;
import main.interfaces.Launchable;
import main.interfaces.Purchasable;

public class Game implements Launchable, Purchasable {
    private String id;
    private String title;
    private String genre;
    private double price;
    private String imagePath; 

    private Game(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.genre = builder.genre;
        this.price = builder.price;
        this.imagePath = builder.imagePath;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; } 

    @Override
    public void launch() { System.out.println("Launching " + title); }

    @Override
    public void purchase(double balance) throws SaldoKurangException {
        if (balance < price) {
            throw new SaldoKurangException(balance, price);
        }
    }

    @Override
    public String toString() { return title; }

    public static class Builder {
        private String id;
        private String title;
        private String genre;
        private double price;
        private String imagePath; 

        public Builder setId(String id) { this.id = id; return this; }
        public Builder setTitle(String title) { this.title = title; return this; }
        public Builder setGenre(String genre) { this.genre = genre; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }
        public Builder setImagePath(String imagePath) { this.imagePath = imagePath; return this; }

        public Game build() { return new Game(this); }
    }
}