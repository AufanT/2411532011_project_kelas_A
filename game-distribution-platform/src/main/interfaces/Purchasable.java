package main.interfaces;
import main.error.SaldoKurangException;

public interface Purchasable {
    void purchase(double balance) throws SaldoKurangException;
}