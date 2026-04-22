package pl.mentor.banking.analyzer.loader;

import com.opencsv.CSVReader;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvTransactionLoader extends BaseFileLoader{

    public CsvTransactionLoader() {
        super();
        System.out.println("CsvTransactionLoader konstruktor end after super(BaseFileLoader)");
    }

    @Override
    protected List<Transaction> parseTransactionData(String path) {
        List<Transaction> transactions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new java.io.FileReader(path))) {
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

        } catch (Exception e) {
            // Łapiemy wszystko: błędy liczb, dat, brakujące kolumny
            throw new RuntimeException("KRYTYCZNY BŁĄD DANYCH: Plik CSV jest uszkodzony lub zawiera błędy w linii. " +
                    "Operacja przerwana dla zachowania spójności danych.", e);
        }

        return transactions;
    }
}