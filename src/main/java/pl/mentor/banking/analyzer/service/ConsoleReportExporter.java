package pl.mentor.banking.analyzer.service;

public class ConsoleReportExporter implements ReportExporter{
    @Override
    public void export(String reportContent) {
        System.out.println(reportContent);
    }
}
