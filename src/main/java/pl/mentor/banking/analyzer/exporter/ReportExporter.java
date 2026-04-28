package pl.mentor.banking.analyzer.exporter;

import pl.mentor.banking.analyzer.exception.ReportExportException;

public interface ReportExporter {
    void export(String reportContent) throws ReportExportException;
}
