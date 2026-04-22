package pl.mentor.banking.analyzer.loader;

import pl.mentor.banking.analyzer.model.Transaction;
import java.util.List;

public interface TransactionSource {
    List<Transaction> loadTransactions(String path);
}