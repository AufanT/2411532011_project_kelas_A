public final class Mobil extends Kendaraan implements BahanBakar {
    private String jenisTransmisi;

    public Mobil(String merk, String model, int tahunProduksi, String jenisTransmisi) {
        super(merk, model, tahunProduksi);
        this.jenisTransmisi = jenisTransmisi;
    }

    public void nyalakanMesin() {
        System.out.println("Nyalakan mesin: tekan tombol start");
    }

    public String jenisBahanBakar() {
        return "Bensin";
    }

    public void fiturMobile() {
        System.out.println("Fitur mobil: AC, Power Steering, Audio System");
    }

}
