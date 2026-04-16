package pl.mentor.banking.analyzer;

import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.payment.PaymentTerminal;
import pl.mentor.banking.analyzer.service.CsvTransactionLoader;
import pl.mentor.banking.analyzer.service.VisaService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicjalizacja loadera
        CsvTransactionLoader loader = new CsvTransactionLoader();

        // 2. Próba wczytania danych z pliku w resources
        System.out.println("--- Rozpoczynam wczytywanie danych z CSV ---");
        List<Transaction> transactions = loader.loadTransactions("transactions.csv");

        // 3. Wyświetlenie wyników
        if (transactions.isEmpty()) {
            System.out.println("Nie wczytano żadnych transakcji. Sprawdź plik CSV i logi błędów.");
        } else {
            System.out.println("Sukces! Wczytano " + transactions.size() + " transakcji:");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }

        VisaService visaService = new VisaService();
        PaymentTerminal terminal = new PaymentTerminal(visaService);
        System.out.println("Uruchamiam płatność kartą...");
        terminal.performPayment(100.0);

    }
}