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
}
