package pl.mentor.banking.analyzer.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(String id, BigDecimal value, String currency, LocalDate date, TransactionCategory category) {
//    Metoda dodana w celu treningu - nie używam póki co
    public void isNegative(){
        if(value.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}
