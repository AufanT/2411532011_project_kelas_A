package src;

public class Main {
    public static void main(String[] args) {
        Book novel = new Novel("Drama", "Laskar Pelangi", "Andrea Hirata");
        Book magazine = new Magazine("Science", "National Geographic", "Various Authors");
        Book textbook = new Textbook("Informatika", "Pemrograman Java", "Anonimous");

        User user = new User();

        System.out.println("\n=== Detail Buku ===");
        user.viewBookDetails(novel);
        System.out.println();
        user.viewBookDetails(magazine);
        System.out.println();
        user.viewBookDetails(textbook);
        System.out.println();
        
        System.out.println("=== Proses peminjaman buku ===");
        user.borrowBook(novel);
        user.borrowBook(magazine);

        System.out.println("\nStatus buku setelah dipinjam: ");
        System.out.println(novel.getTitle() + " tersedia: " + novel.isAvailable());
        System.out.println(magazine.getTitle() + " tersedia: " + magazine.isAvailable());

        System.out.println("\n=== Proses pengembalian buku ===");
        user.returnBook(novel);

        System.out.println("\nStatus buku setelah dikembalikan: ");
        System.out.println(novel.getTitle() + " tersedia: " + novel.isAvailable());
        
    }
}
