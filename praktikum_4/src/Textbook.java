package src;

public class Textbook extends Book {
    private String bidangStudi;

    public Textbook (String bidangStudi, String title, String author) {
        super(title, author);
        this.bidangStudi = bidangStudi;
    }
    
    public String getBidangStudi() { return bidangStudi; }
    
}
