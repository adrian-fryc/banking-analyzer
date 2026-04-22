package pl.mentor.banking.analyzer.loader;

import pl.mentor.banking.analyzer.model.Transaction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class BaseFileLoader implements TransactionSource{
    @Override
    public final List<Transaction> loadTransactions(String path) {
        // 1. Logika managera (wspólna dla wszystkich plików)
        System.out.println("LOG: Sprawdzam dostępność pliku: " + path);
        Path checkPath = Paths.get(path);
        if (Files.exists(checkPath)) {
            System.out.println("Plik istnieje.");
        } else {
            System.out.println("Plik nie istnieje.");
            throw new RuntimeException("Plik nie istnieje: " + path );
        }

        // 2. Wywołanie specjalisty (Dziecka)
        return parseTransactionData(path);
    }

    protected abstract List<Transaction> parseTransactionData(String path);

}
