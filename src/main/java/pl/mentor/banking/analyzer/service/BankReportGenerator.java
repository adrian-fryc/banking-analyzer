package pl.mentor.banking.analyzer.service;

import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.util.Map;

public class BankReportGenerator {
    private final TransactionAnalyzer analyzer;

    // Konstruktor przyjmuje "gotowy mózg"
    public BankReportGenerator(TransactionAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void printReport() {
        System.out.println("********** RAPORT TRANSAKCJI **********");

        // Sumaryczne wydatki
        BigDecimal totalFood = analyzer.calculateTotalSpentInCategory(TransactionCategory.FOOD);
        System.out.println("Wydatki na jedzenie: " + totalFood + " PLN");

        // Najwyższa transakcja
        analyzer.findHighestTransaction().ifPresent(t ->
                System.out.println("Najwyższa operacja: " + t.amount() + " (" + t.category() + ")")
        );

        // Wydatki wg kategorii (wykorzystujemy Twoją mapę!)
        System.out.println("\nPODZIAŁ NA KATEGORIE:");
        analyzer.calculateExpensesByCategory().forEach((category, sum) ->
                System.out.println("- " + category + ": " + sum + " PLN")
        );

        // Wydatki wg kategorii posortowane od najwiekszych kwot (wykorzystujemy Twoją mapę!)
        System.out.println("\nPODZIAŁ NA KATEGORIE posortowane:");
        analyzer.calculateExpensesByCategory()
                .entrySet()
                .stream()
                // Sortujemy po wartości (BigDecimal) w porządku odwrotnym (reverseOrder)
                .sorted(Map.Entry.<TransactionCategory, BigDecimal>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " PLN")
                );

        System.out.println("***************************************");
    }

    public void generateSummary(ReportExporter exporter) {
        StringBuilder sb = new StringBuilder(); // Nasz "bufor" na tekst
        var summary = analyzer.calculateExpensesByCategory();

        // Zamiast System.out.println, używamy sb.append()
        sb.append("========================================\n");
        sb.append("           RAPORT FINANSOWY             \n");
        sb.append("========================================\n");

        if (summary.isEmpty()) {
            sb.append("Brak danych do wyświetlenia.\n");
        } else {
            summary.entrySet().stream()
                    .sorted(Map.Entry.<TransactionCategory, BigDecimal>comparingByValue().reversed())
                    .forEach(entry -> {
                        // Składamy tekst w formacie "Kategoria: Suma"
                        String row = String.format("- %-15s: %10.2f PLN\n",
                                entry.getKey(), entry.getValue());
                        sb.append(row);
                    });

            sb.append("----------------------------------------\n");

            analyzer.findHighestTransaction().ifPresent(t -> {
                String highest = String.format("NAJWYŻSZA TRANSAKCJA: %s (%.2f %s)\n",
                        t.category(), t.amount(), t.currency());
                sb.append(highest);
            });
        }
        sb.append("========================================\n");

        BigDecimal average = analyzer.calculateAverageTransactionAmount();
        sb.append(String.format("Średni wydatek: %10.2f PLN\n", average));

        // NA KONIEC: Wysyłamy gotowy tekst do eksportera!
        exporter.export(sb.toString());
    }
}
