package pl.mentor.banking.analyzer.exporter;

import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.BaseFont;
import org.openpdf.text.pdf.PdfWriter;
import pl.mentor.banking.analyzer.exception.ReportExportException;
import pl.mentor.banking.analyzer.model.ExportMetadata;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfReportExporter implements ReportExporter{
    private final ExportMetadata metadata;

    public PdfReportExporter(ExportMetadata metadata){
        this.metadata = metadata;
    }

    @Override
    public void export(String reportContent) {
        Document document = new Document();
        // Try-with-resources automatycznie zamknie FileOutputStream
        try (FileOutputStream fos = new FileOutputStream(metadata.fileName())) {
            PdfWriter.getInstance(document, fos);
            document.open();
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(baseFont);
            font.setSize(12);
            document.add(new Paragraph("Raport ąćźśćńółęĄĆÓŹŃĘŚŹŻ", font));
            document.add(new Paragraph("_________________________________________________________", font));
            document.add(new Paragraph("Autor: " + metadata.author(), font));
            document.add(new Paragraph("Data: " + metadata.createdAt(), font));
            document.add(new Paragraph("_________________________________________________________", font));
            // Twój świetny pomysł ze Stream API!
            reportContent.lines()
                    .forEach(line -> document.add(new Paragraph(line, font)));

            document.close();
        } catch (Exception e) {
            // Opakowujemy techniczny błąd w nasz własny wyjątek
            throw new ReportExportException("Nie udało się wygenerować PDF: " + e.getMessage());
        }
    }
}
