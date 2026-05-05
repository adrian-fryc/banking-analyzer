package pl.mentor.banking.analyzer.exporter;

import pl.mentor.banking.analyzer.exception.ReportExportException;
import pl.mentor.banking.analyzer.model.ReportData;

public class ConsoleReportExporter implements ReportExporter{
    @Override
    public void export(ReportData data) throws ReportExportException {
        System.out.println("=== " + data.title() + " ===");
        data.rows().forEach((key, value) ->
                System.out.printf("- %-15s: %10.2f PLN%n", key, value)
        );
        System.out.println("---------------------------------");
        System.out.println(data.footerInfo());
    }
//    @Override
//    public void export(String reportContent) {
//        System.out.println(reportContent);
//    }
}
