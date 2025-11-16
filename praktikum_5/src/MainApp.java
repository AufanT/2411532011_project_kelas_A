public class MainApp {
    public static void main(String[] args) {
        // output class Mobil
        Mobil mobilSaya = new Mobil("Toyota", "Avanza", 2021, "Otomatis");
        mobilSaya.tampilkanInfo();
        mobilSaya.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + mobilSaya.jenisBahanBakar());
        mobilSaya.infoKonsumsi();
        mobilSaya.fiturMobile();

        System.out.println();
        
        // output class Bus
        Bus busSekolah = new Bus("Mercedes-Benz", "Bus Pariwisata", 2018, "Ekonomi");
        busSekolah.tampilkanInfo();
        busSekolah.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + busSekolah.jenisBahanBakar());
        System.out.println("Kapasitas Penumpang: " + busSekolah.kapasitasPenumpang());
        busSekolah.fiturBus();
        Bus.JadwalPerjalanan jadwal = busSekolah.new JadwalPerjalanan("Jakarta - Bandung", "08:00 AM");
        jadwal.tampilkanJadwal();
        
        System.out.println();

        // Output class Pesawat
        Pesawat pesawatSaya = new Pesawat("Garuda", "Boeing 737", 2020, "Komersial", "Garuda Indonesia");
        pesawatSaya.tampilkanInfo();
        pesawatSaya.nyalakanMesin();
        System.out.println("Jenis Bahan Bakar: " + pesawatSaya.jenisBahanBakar());
    }
}
