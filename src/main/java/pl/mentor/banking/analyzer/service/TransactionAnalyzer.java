package pl.mentor.banking.analyzer.service;

import pl.mentor.banking.analyzer.exception.NoTransactionsException;
import pl.mentor.banking.analyzer.loader.TransactionSource;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa służąca do analizy finansowej transakcji bankowych.
 * Autor Jan Brzechwa
 */

public class TransactionAnalyzer {
    private final List<Transaction> transactions;

    public TransactionAnalyzer(TransactionSource loader, String path){
        System.out.println("TransactionAnalyzer konstruktor start");
        this.transactions = loader.loadTransactions(path);
        System.out.println("TransactionAnalyzer konstruktor end");
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public BigDecimal calculateTotalSpentInCategory(TransactionCategory category){
        return transactions.stream()
                .filter(t -> t.category().equals(category))
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> findTransactionsAbove(BigDecimal threshold){
        return transactions.stream()
                .filter(t-> t.amount().compareTo(threshold) > 0)
                .toList();
    }

    public Optional<Transaction> findHighestTransaction(){
        return transactions.stream()
                .max(Comparator.comparing(Transaction::amount));
    }

    public Map<TransactionCategory, BigDecimal> calculateExpensesByCategory(){
        return calculateExpensesByCategory(this.transactions);
    }

    public Map<TransactionCategory, BigDecimal> calculateExpensesByCategory(List<Transaction> source){
        return source.stream()
                .collect(Collectors.groupingBy(Transaction::category, Collectors.reducing(BigDecimal.ZERO, Transaction::amount, BigDecimal::add)));
    }

//    Meotda do obliczania średniej z transakcji
    public BigDecimal calculateAverageTransactionAmount() {
        if(transactions != null && !transactions.isEmpty()){
            return transactions.stream()
                    .map(Transaction::amount) // Wyciągnij same kwoty
                    // W Streamach dla BigDecimal nie ma gotowego .average(),
                    // więc musimy zsumować i podzielić przez rozmiar listy.
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(transactions.size()), 2, RoundingMode.HALF_UP);
        }else{
            throw new NoTransactionsException("Nie można policzyć średniej dla pustej listy transakcji");
        }
    }

    public List<Transaction> findTransactionsInMonth(YearMonth yearMonth) {
        return transactions.stream()
                .filter(t -> t.date().getMonth() == yearMonth.getMonth() && t.date().getYear() == yearMonth.getYear())
                .toList();
    }

    public BigDecimal calculateTotalInMonth(YearMonth yearMonth) {
        List<Transaction> transactionsInMonth = findTransactionsInMonth(yearMonth);
        if(transactionsInMonth != null && !transactionsInMonth.isEmpty()){
            return transactionsInMonth.stream()
                    .map(Transaction::amount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }else{
            return BigDecimal.ZERO;
        }
    }
}
