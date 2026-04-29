package pl.mentor.banking.analyzer.exporter;

import pl.mentor.banking.analyzer.exception.ReportExportException;
import pl.mentor.banking.analyzer.model.ReportData;

public interface ReportExporter {
    void export(ReportData data) throws ReportExportException;
}
