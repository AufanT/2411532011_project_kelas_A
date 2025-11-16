public class Bus extends Kendaraan implements TransportasiUmum {
    String kelasBus;

    public Bus(String merk, String model, int tahunProduksi, String kelasBus) {
        super(merk, model, tahunProduksi);
        this.kelasBus = kelasBus;
    }

    public void nyalakanMesin() {
        System.out.println("Nyalakan Mesin: Putar kunci untuk menyalakan");
    }

    public String jenisBahanBakar() {
        return "Solar";
    }

    public int kapasitasPenumpang() {
        return 45;
    }

    public void fiturBus() {
        System.out.println("Fitur Bus: Dilengkapi kursi nyaman dan fasilitas hiburan");
    }

    public class JadwalPerjalanan {
        String rute, waktuBerangkat;

        public JadwalPerjalanan(String rute, String waktuBerangkat) {
            this.rute = rute;
            this.waktuBerangkat = waktuBerangkat;
        }

        public void tampilkanJadwal() {
            System.out.println("Jadwal Perjalanan: Rute " + rute + " berangkat pada " + waktuBerangkat);
        }   
    }
}
