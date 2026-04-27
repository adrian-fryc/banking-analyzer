package pl.mentor.banking.analyzer;

import pl.mentor.banking.analyzer.exporter.ConsoleReportExporter;
import pl.mentor.banking.analyzer.exporter.FileReportExporter;
import pl.mentor.banking.analyzer.loader.CsvTransactionLoader;
import pl.mentor.banking.analyzer.loader.TransactionLoaderFactory;
import pl.mentor.banking.analyzer.loader.TransactionSource;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;
import pl.mentor.banking.analyzer.service.*;

import java.time.YearMonth;

public class Main {
    public static void main(String[] args) {
        // 1. Inicjalizacja loadera
        String path = "D:\\Programowanie(NAUKA)\\banking-analyzer\\src\\main\\resources\\transactions.json";
        TransactionSource loader = TransactionLoaderFactory.getLoader(path);

        // 2. Próba wczytania danych z pliku w resources
//        System.out.println("--- Rozpoczynam wczytywanie danych z CSV ---");
//        List<Transaction> transactions = loader.loadTransactions("D:\\Programowanie(NAUKA)\\banking-analyzer\\src\\main\\resources\\transactions.csv");
        TransactionAnalyzer analyzer = new TransactionAnalyzer(loader, path);
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
            reportGenerator.generateSummary(new ConsoleReportExporter());
            reportGenerator.generateMonthlyReport(YearMonth.of(2026, 5), new FileReportExporter("moj_raport_2026_05.txt"));
//            reportGenerator.generateSummary(new FileReportExporter("moj_raport.txt")); // Do pliku

            reportGenerator.generateAnnualReport(2026, new ConsoleReportExporter());

            var a = analyzer.filterTransactions(t -> t.category().equals(TransactionCategory.FUEL));
            System.out.println("Filtered transactions: " + a);

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