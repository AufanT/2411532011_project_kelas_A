public class Pesawat extends Kendaraan implements TransportasiUdara, Maskapai {
    private String tipePesawat;
    private String namaMaskapai;

    public Pesawat(String merk, String model, int tahunProduksi, String tipePesawat, String namaMaskapai) {
        super(merk, model, tahunProduksi);
        this.tipePesawat = tipePesawat;
        this.namaMaskapai = namaMaskapai;
    }

    public void nyalakanMesin() {
        System.out.println("Nyalakan mesin: Bersiap lepas landas");
    }

    public String jenisPenerbangan() {
        return tipePesawat;
    }

    public String namaMaskapai() {
        return namaMaskapai;
    }

    public String jenisBahanBakar() {
        return "Avtur";
    }

}
