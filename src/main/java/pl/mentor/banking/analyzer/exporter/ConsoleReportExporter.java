package pl.mentor.banking.analyzer.exporter;

public class ConsoleReportExporter implements ReportExporter{
    @Override
    public void export(String reportContent) {
        System.out.println(reportContent);
    }
}
