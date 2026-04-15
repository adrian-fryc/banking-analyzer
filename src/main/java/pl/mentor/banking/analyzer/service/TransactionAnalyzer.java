package pl.mentor.banking.analyzer.service;

import com.opencsv.CSVReader;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Klasa służąca do analizy finansowej transakcji bankowych.
 * Autor Jan Brzechwa
 */

public class TransactionAnalyzer {
    private final List<Transaction> transactions;

    public TransactionAnalyzer(List<Transaction> transactions){
        this.transactions = List.copyOf(transactions);
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
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::category, Collectors.reducing(BigDecimal.ZERO, Transaction::amount, BigDecimal::add)));
    }
}
