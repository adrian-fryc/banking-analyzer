package pl.mentor.banking.analyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.mentor.banking.analyzer.model.Transaction;

import java.io.IOException;
import java.util.List;

public class JsonTransactionLoader extends BaseFileLoader{

    private final ObjectMapper objectMapper;

    public JsonTransactionLoader() {
        // Konfigurujemy Jackstona, żeby umiał czytać daty Java 8+
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected List<Transaction> parseTransactionData(String path) {
        System.out.println("LOG: Specjalista JSON parsuje plik: " + path);
        try {
            // Jackson potrafi w jednej linijce przeczytać plik i zamienić go w listę!
            return objectMapper.readValue(
                    new java.io.File(path),
                    new TypeReference<List<Transaction>>() {}
            );
        } catch (IOException e) {
            System.err.println("Błąd podczas czytania JSON: " + e.getMessage());
            return List.of();
        }
    }
}
