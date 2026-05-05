package pl.mentor.banking.analyzer.exporter;

import pl.mentor.banking.analyzer.exception.ReportExportException;
import pl.mentor.banking.analyzer.model.ReportData;

public class FileReportExporter implements ReportExporter{
    private final String fileName;

    public FileReportExporter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void export(ReportData data) throws ReportExportException {
       try (java.io.PrintWriter writer = new java.io.PrintWriter(fileName)) {
            writer.println("=== " + data.title() + " ===");
            data.rows().forEach((key, value) ->
                    writer.printf("- %-15s: %10.2f PLN%n", key, value)
            );
            writer.println("---------------------------------");
            writer.println(data.footerInfo());

            System.out.println("LOG: Raport został zapisany do pliku: " + fileName);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Błąd zapisu raportu: " + e.getMessage());
        }
    }


//    @Override
//    public void export(String reportContent) {
//        try (java.io.PrintWriter writer = new java.io.PrintWriter(fileName)) {
//            writer.print(reportContent);
//            System.out.println("LOG: Raport został zapisany do pliku: " + fileName);
//        } catch (java.io.FileNotFoundException e) {
//            System.err.println("Błąd zapisu raportu: " + e.getMessage());
//        }
//    }
}
