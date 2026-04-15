package pl.mentor.banking.analyzer.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(String id, BigDecimal amount, String currency, LocalDate date, TransactionCategory category) {
}
