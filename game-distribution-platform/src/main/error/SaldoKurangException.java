package main.error;

public class SaldoKurangException extends Exception {
    public SaldoKurangException(double saldoSaatIni, double hargaGame) {
        super("Saldo tidak cukup! Saldo: Rp " + saldoSaatIni + ", Harga: Rp " + hargaGame);
    }
}