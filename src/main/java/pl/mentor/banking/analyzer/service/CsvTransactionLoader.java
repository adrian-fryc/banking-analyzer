package pl.mentor.banking.analyzer.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvTransactionLoader implements TransactionSource{

    public List<Transaction> loadTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new IllegalArgumentException("Nie znaleziono pliku: " + fileName);
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                // Mapujemy kolumny zgodnie z Twoim rekordem:
                // id, amount, currency, date, category
                String id = line[0];
                BigDecimal amount = new BigDecimal(line[1]);
                String currency = line[2];
                LocalDate date = LocalDate.parse(line[3]);
                // Zamiana Stringa na Enum (musi pasować wielkością liter!)
                TransactionCategory category = TransactionCategory.valueOf(line[4].toUpperCase());

                transactions.add(new Transaction(id, amount, currency, date, category));
            }

        } catch (IOException | CsvValidationException e) {
            System.err.println("Błąd podczas wczytywania: " + e.getMessage());
        }

        return transactions;
    }
}