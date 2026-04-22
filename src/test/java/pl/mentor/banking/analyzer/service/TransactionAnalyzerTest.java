package pl.mentor.banking.analyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mentor.banking.analyzer.exception.NoTransactionsException;
import pl.mentor.banking.analyzer.loader.TransactionSource;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAnalyzerTest {
    private TransactionAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        // Arrange - wspólne dla wszystkich testów
        TransactionSource mockLoader = path -> List.of(
                new Transaction("1", new BigDecimal("100.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", new BigDecimal("50.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("3", new BigDecimal("1000.00"), "PLN", LocalDate.now(), TransactionCategory.SALARY)
        );
        this.analyzer = new TransactionAnalyzer(mockLoader, "any-path-doesnt-matter.csv");
    }

    @Test
    void calculateTotalSpentInCategory(){
        BigDecimal result = analyzer.calculateTotalSpentInCategory(TransactionCategory.FOOD);
//        assertEquals(new BigDecimal("150.00"), result);
        assertTrue(new BigDecimal("150.00").compareTo(result) == 0);
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
//        assertEquals(new BigDecimal("1000.00"), result.get().amount());
        assertTrue(new BigDecimal("150.00").compareTo(result.get().amount()) == 0);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoTransactions(){
        TransactionSource mockLoader = path -> List.of();
        var analyzerEmpty = new TransactionAnalyzer(mockLoader, "");
        var result = analyzerEmpty.findHighestTransaction();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnZeroAverageWhenNoTransactions() {
        // Arrange: pusty loader
        TransactionSource emptyLoader = path -> List.of();
        var analyzerEmpty = new TransactionAnalyzer(emptyLoader, "");

        // Act & Assert (Robimy to w jednej linii!)
        var exception = assertThrows(NoTransactionsException.class, analyzerEmpty::calculateAverageTransactionAmount, "Tu powinien być Brak transakcji"
        );
        // Opcjonalnie: Sprawdź, czy wiadomość w błędzie jest taka, jak chciałeś
        assertTrue(exception.getMessage().contains("Brak transakcji"));
    }
}
