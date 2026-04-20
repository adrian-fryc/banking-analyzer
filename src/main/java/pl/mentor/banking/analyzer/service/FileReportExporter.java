package pl.mentor.banking.analyzer.service;

public class FileReportExporter implements ReportExporter{
    private final String fileName;

    public FileReportExporter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void export(String reportContent) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(fileName)) {
            writer.print(reportContent);
            System.out.println("LOG: Raport został zapisany do pliku: " + fileName);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Błąd zapisu raportu: " + e.getMessage());
        }
    }
}
