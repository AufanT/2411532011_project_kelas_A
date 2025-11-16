public interface  BahanBakar {
    String jenisBahanBakar();

    default void infoKonsumsi() {
        System.out.println("Info konsumsi: Konsumsi bahan bakar tergantung kapasitas mesin.");
    }
}
