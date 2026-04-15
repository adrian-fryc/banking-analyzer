package pl.mentor.banking.analyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionAnalyzerTest {
    private TransactionAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        // Arrange - wspólne dla wszystkich testów
        var transactions = List.of(
                new Transaction("1", new BigDecimal("100.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", new BigDecimal("50.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("3", new BigDecimal("1000.00"), "PLN", LocalDate.now(), TransactionCategory.SALARY)
        );
        this.analyzer = new TransactionAnalyzer(transactions);
    }

    @Test
    void calculateTotalSpentInCategory(){
        BigDecimal result = analyzer.calculateTotalSpentInCategory(TransactionCategory.FOOD);
        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    void shouldReturnZeroForEmptyCategory() {
        // Act
        BigDecimal result = analyzer.calculateTotalSpentInCategory(TransactionCategory.FUEL);
        // Assert
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldFindHighestTransaction(){
        var result = analyzer.findHighestTransaction();
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("1000.00"), result.get().amount());
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoTransactions(){
        List<Transaction> emptyList = List.of();
        var analyzerEmpty = new TransactionAnalyzer(emptyList);
        var result = analyzerEmpty.findHighestTransaction();
        assertTrue(result.isEmpty());
    }
}
