package pl.mentor.banking.analyzer.model;

import java.time.LocalDateTime;

public record ExportMetadata(String fileName, String author, LocalDateTime createdAt, boolean isConfidential) {
}
