package src;


public class Magazine extends Book {
    private String kategori;

    public Magazine(String kategori, String title, String author) {
        super(title, author);
        this.kategori = kategori;
    }
    
    public String getKategori() { return kategori; }

    
}
