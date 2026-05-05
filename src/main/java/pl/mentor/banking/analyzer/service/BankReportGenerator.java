package pl.mentor.banking.analyzer.service;

import pl.mentor.banking.analyzer.exporter.ReportExporter;
import pl.mentor.banking.analyzer.model.ReportData;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
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

    private void appendCategoryTable(StringBuilder sb, Map<TransactionCategory, BigDecimal> summary){
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

        }
    }

    private <K extends Comparable<? super K>> void appendGenericTable(StringBuilder sb, Map<K, BigDecimal> summary){
        if (summary.isEmpty()) {
            sb.append("Brak danych do wyświetlenia.\n");
        } else {
            summary.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        // Składamy tekst w formacie "Kategoria: Suma"
                        String row = String.format("- %-15s: %10.2f PLN\n",
                                entry.getKey(), entry.getValue());
                        sb.append(row);
                    });

            sb.append("----------------------------------------\n");

        }
    }

    public void generateSummary(ReportExporter exporter) {
//        StringBuilder sb = new StringBuilder(); // Nasz "bufor" na tekst
        var summary = analyzer.calculateExpensesByCategory();

        // Zamiast System.out.println, używamy sb.append()
//        sb.append("========================================\n");
//        sb.append("           RAPORT FINANSOWY             \n");
//        sb.append("========================================\n");
//        appendCategoryTable(sb, summary);

        Map<String, BigDecimal> dataReport = new HashMap<>();

        analyzer.findHighestTransaction().ifPresent(t -> {
            String highest = String.format("NAJWYŻSZA TRANSAKCJA: %s (%.2f %s)\n",
                    t.category(), t.amount(), t.currency());
//            sb.append(highest);
            dataReport.put("NAJWYŻSZA TRANSAKCJA w " + t.currency(), t.amount());
        });
//        sb.append("========================================\n");

        BigDecimal average = analyzer.calculateAverageTransactionAmount();
//        sb.append(String.format("Średni wydatek: %10.2f PLN\n", average));

        // NA KONIEC: Wysyłamy gotowy tekst do eksportera!
//        exporter.export(sb.toString());
        ReportData data = new ReportData(
                "Brak transakcji w podanym miesiącu",
                dataReport,
                "Średni wydatek: " + average + " PLN"
        );
        exporter.export(data);
    }

    public void generateMonthlyReport(YearMonth yearMonth, ReportExporter exporter){
//        StringBuilder sb = new StringBuilder(); // Nasz "bufo
//        sb.append("========================================\n");
//        sb.append("  RAPORT FINANSOWY ZA MIESIĄC " + yearMonth.toString() +" \n");
//        sb.append("========================================\n");
        Map<String, BigDecimal> dataReport = new HashMap<>();
        var monthlyTransactions = analyzer.findTransactionsInMonth(yearMonth);
        if(monthlyTransactions.isEmpty()){
//            sb.append("Brak transakcji w podanym miesiącu");
            ReportData data = new ReportData(
                    "Brak transakcji w podanym miesiącu",
                    dataReport,
                    "Suma całkowita: " + /* oblicz sumę */ " PLN"
            );
//            exporter.export(sb.toString());
            exporter.export(data);
            return;
        }else{
            var summary = analyzer.calculateExpensesByCategory(monthlyTransactions);
//            appendCategoryTable(sb, summary);
            summary.forEach((category, amount) ->
                    dataReport.put(category.toString(), amount)
            );
            var total = analyzer.calculateTotalInMonth(yearMonth);
            ReportData data = new ReportData(
                    "RAPORT MIESIĘCZNY: " + yearMonth,
                    dataReport,
                    "Suma całkowita: " + total + " PLN"
            );


//            sb.append("SUMA WYDATKÓW: ").append(total).append(" PLN\n");
//            exporter.export(sb.toString());
            exporter.export(data);
        }
    }

    public void generateAnnualReport(int year, ReportExporter exporter){
//        StringBuilder strB = new StringBuilder(); // Nasz "bufor
//        strB.append("========================================\n");
//        strB.append("  RAPORT FINANSOWY ZA ROK ").append(year).append(" \n");
//        strB.append("========================================\n");
        Map<String, BigDecimal> monthlyTotals = new HashMap<>();
        for (Month month : Month.values()) {
            monthlyTotals.put(month.toString(), analyzer.calculateTotalInMonth(YearMonth.of(year, month)));
        }
        var totalAmount = monthlyTotals.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        appendGenericTable(strB, monthlyTotals);
//        exporter.export(strB.toString());
        ReportData data = new ReportData(
                "RAPORT ROCZNY " + year,
                monthlyTotals,
                "Suma całkowita: " + totalAmount + " PLN"
        );

        exporter.export(data); // Teraz pasuje!
    }
}
