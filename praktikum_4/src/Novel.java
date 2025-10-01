package src;


public class Novel extends Book {
    private String genre;

    public Novel(String genre, String title, String author) {
        super(title, author);
        this.genre = genre;
    }
    
    public String getGenre() { return genre; }
}
