package pl.mentor.banking.analyzer;

import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.payment.PaymentMethod;
import pl.mentor.banking.analyzer.payment.PaymentTerminal;
import pl.mentor.banking.analyzer.payment.service.BlikService;
import pl.mentor.banking.analyzer.payment.service.VisaService;
import pl.mentor.banking.analyzer.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicjalizacja loadera
        TransactionSource loader = new CsvTransactionLoader();

        // 2. Próba wczytania danych z pliku w resources
//        System.out.println("--- Rozpoczynam wczytywanie danych z CSV ---");
//        List<Transaction> transactions = loader.loadTransactions("D:\\Programowanie(NAUKA)\\banking-analyzer\\src\\main\\resources\\transactions.csv");
        TransactionAnalyzer analyzer = new TransactionAnalyzer(loader, "D:\\Programowanie(NAUKA)\\banking-analyzer\\src\\main\\resources\\transactions.csv");
        // 3. Wyświetlenie wyników
        if (analyzer.getTransactions().isEmpty()) {
            System.out.println("Nie wczytano żadnych transakcji. Sprawdź plik CSV i logi błędów.");
        } else {
            System.out.println("Sukces! Wczytano " + analyzer.getTransactions().size() + " transakcji:");
            for (Transaction t : analyzer.getTransactions()) {
                System.out.println(t);
            }

            System.out.println("analyzer findHighestTransaction: " + analyzer.findHighestTransaction());

            BankReportGenerator reportGenerator = new BankReportGenerator(analyzer);
            reportGenerator.printReport();

        }

//        loader = new JsonTransactionLoader();
//        System.out.println("--- Rozpoczynam wczytywanie danych z JSON ---");
//        analyzer = new TransactionAnalyzer(loader, "D:\\Programowanie(NAUKA)\\banking-analyzer\\src\\main\\resources\\transactions.json");
//
//        // 3. Wyświetlenie wyników
//        if (analyzer.getTransactions().isEmpty()) {
//            System.out.println("Nie wczytano żadnych transakcji. Sprawdź plik JSON i logi błędów.");
//        } else {
//            System.out.println("Sukces! Wczytano " + analyzer.getTransactions().size() + " transakcji:");
//            for (Transaction t : analyzer.getTransactions()) {
//                System.out.println(t);
//            }
//        }
/*
        PaymentMethod paymentMethod = new VisaService();
        PaymentTerminal terminal = new PaymentTerminal(paymentMethod);
        System.out.println("Uruchamiam płatność kartą...");
        terminal.performPayment(100.0);

        System.out.println("\n\n");

        BlikService blikService = new BlikService();
        PaymentTerminal terminal2 = new PaymentTerminal(blikService);
        System.out.println("Uruchamiam płatność blik...");
        terminal2.performPayment(50.0);

 */
    }
}