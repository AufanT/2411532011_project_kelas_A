package src;

public class User {
    String name;

    public User(String name) {
        this.name = name;
    }

    public User() {
        this.name = "Superman";
    }

    public void viewBookDetails(Book book) {
        System.out.println("Judul: " + book.getTitle());
        System.out.println("Penulis: " + book.getAuthor());
        System.out.println("Tersedia: " + (book.isAvailable()? "Ya" : "Tidak"));

        if ( book instanceof Novel ) {  
            Novel novel = (Novel) book;
            System.out.println("Genre: " + novel.getGenre());
        }
        if ( book instanceof Magazine ) {  
            Magazine magazine = (Magazine) book;
            System.out.println("Kategori: " + magazine.getKategori());
        }
        if ( book instanceof Textbook ) {  
            Textbook textbook = (Textbook) book;
            System.out.println("Bidang Studi: " + textbook.getBidangStudi());
        }
    }   

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.borrowBook();
            System.out.println("Buku \"" + book.getTitle() + "\" berhasil dipinjam oleh " + this.name);
        } else {
            System.out.println("Maaf " + this.name + ", buku \"" + book.getTitle() + "\" sedang tidak tersedia.");
        }
    }

    public void returnBook(Book book) {
        if (!book.isAvailable()) {
            book.returnBook();
            System.out.println("Buku \"" + book.getTitle() + "\" berhasil dikembalikan.");
        } else {
            System.out.println("Buku \"" + book.getTitle() + "\" sudah tersedia.");
            
        }
    }
}
