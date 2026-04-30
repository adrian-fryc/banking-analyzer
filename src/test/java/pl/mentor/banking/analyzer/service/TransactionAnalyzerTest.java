package pl.mentor.banking.analyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mentor.banking.analyzer.exception.NoTransactionsException;
import pl.mentor.banking.analyzer.loader.TransactionSource;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionAnalyzerTest {
    @Mock
    private TransactionSource loader;

    @InjectMocks
    private TransactionAnalyzer analyzer;
/*
    @BeforeEach
    void setUp() {
//         Arrange - wspólne dla wszystkich testów
        TransactionSource mockLoader = path -> List.of(
                new Transaction("1", new BigDecimal("100.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", new BigDecimal("50.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("3", new BigDecimal("1000.00"), "PLN", LocalDate.now(), TransactionCategory.SALARY)
        );

        TransactionSource emptyLoader = path -> List.of();
        this.emptyAnalyzer = new TransactionAnalyzer(emptyLoader, "any-path-doesnt-matter.csv");
        this.analyzer = new TransactionAnalyzer(mockLoader, "any-path-doesnt-matter.csv");
    }
*/
//    Stary test gdy nie używałem MOCKITO

//    @Test
//    void calculateTotalSpentInCategory(){
//        BigDecimal result = analyzer.calculateTotalSpentInCategory(TransactionCategory.FOOD);
//        assertEquals(0, new BigDecimal("150.00").compareTo(result));
//    }
    @Test
    void calculateTotalSpentInCategory(){
        // 1. Arrange (Scenariusz dla aktora)
        List<Transaction> transactions = List.of(
                new Transaction("1", new BigDecimal("100.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", new BigDecimal("50.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD)
        );

        // "Kiedy ktokolwiek zawoła loadTransactions z dowolnym Stringiem, zwróć tę listę"
        when(loader.loadTransactions(anyString())).thenReturn(transactions);

        // 2. Ręczne stworzenie obiektu - TERAZ konstruktor pobierze dane z "zaprogramowanego" loadera
        analyzer = new TransactionAnalyzer(loader, "transactions.json");

        // 3. Act
        BigDecimal result = analyzer.calculateTotalSpentInCategory(TransactionCategory.FOOD);
        System.out.println("AAA " + result);

        // 4. Assert
        assertEquals(0, new BigDecimal("150.00").compareTo(result));

        // 5. Verify (Opcjonalne, ale profesjonalne) - sprawdź czy loader został zawołany
        verify(loader).loadTransactions(anyString());
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
        assertTrue(new BigDecimal("1000.00").compareTo(result.get().amount()) == 0);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoTransactions(){
        var result = analyzer.findHighestTransaction();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnZeroAverageWhenNoTransactions() {
        // Arrange: pusty loader
//        TransactionSource emptyLoader = path -> List.of();
//        var analyzerEmpty = new TransactionAnalyzer(emptyLoader, "");

        // Act & Assert (Robimy to w jednej linii!)
        var exception = assertThrows(NoTransactionsException.class, analyzer::calculateAverageTransactionAmount, "Tu powinien być Brak transakcji"
        );
        // Opcjonalnie: Sprawdź, czy wiadomość w błędzie jest taka, jak chciałeś
        assertTrue(exception.getMessage().contains("Brak transakcji"));
    }

    @Test
    void shouldFindClosestTransaction() {
        var result = analyzer.findClosestTransaction(new BigDecimal("100.00"));
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("100.00"), result.get().amount());
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoTransactionsArePresent(){
        var result = analyzer.findClosestTransaction(new BigDecimal("100.00"));
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFilterTransactions(){
        var result = analyzer.filterTransactions(t -> t.category().equals(TransactionCategory.FOOD));
        assertEquals(2, result.transactions().size());
        assertEquals(0, new BigDecimal("150.00").compareTo(result.totalAmount()));
        assertNotNull(result.reportDate());
    }
}
