package pl.mentor.banking.analyzer.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public record ReportData(
        String title,
        Map<String, BigDecimal> rows, // Klucz jako String pozwoli obsłużyć i Kategorie i Miesiące
        String footerInfo             // Tu wrzucisz średnią, najwyższą transakcję itp.
) {}