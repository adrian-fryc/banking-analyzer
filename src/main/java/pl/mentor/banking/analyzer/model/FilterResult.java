package pl.mentor.banking.analyzer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record FilterResult(List<Transaction> transactions, BigDecimal totalAmount, LocalDateTime reportDate) {
}
