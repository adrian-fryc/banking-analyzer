package pl.mentor.banking.analyzer.exporter;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.BaseFont;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import pl.mentor.banking.analyzer.exception.ReportExportException;
import pl.mentor.banking.analyzer.model.ExportMetadata;
import pl.mentor.banking.analyzer.model.PdfStyle;
import pl.mentor.banking.analyzer.model.ReportData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfReportExporter implements ReportExporter{
    private final ExportMetadata metadata;
    private final PdfStyle style;

    public PdfReportExporter(ExportMetadata metadata, PdfStyle style){
        this.metadata = metadata;
        this.style = style;
    }

    @Override
    public void export(ReportData data) {
        Document document = new Document();
        // Try-with-resources automatycznie zamknie FileOutputStream
        try (FileOutputStream fos = new FileOutputStream(metadata.fileName())) {
            PdfWriter.getInstance(document, fos);
            document.open();
            BaseFont baseFont = BaseFont.createFont(style.fontFamily().getTechnicalName(), BaseFont.CP1250, BaseFont.EMBEDDED);
//            Font font = new Font(baseFont);

            int fontStyle = Font.NORMAL;
            if (style.isBold()) fontStyle |= Font.BOLD;
            if (style.isItalic()) fontStyle |= Font.ITALIC;

            Font font = new Font(baseFont, style.fontSize(), fontStyle);

            // 2. Dodanie napisu "POUFNE", jeśli metadata tak mówi
            if (metadata.isConfidential()) { // Zakładam, że dodasz to pole do ExportMetadata
                Font redFont = new Font(baseFont, style.fontSize() + 2, Font.BOLD, style.fontColor());
                Paragraph confidential = new Paragraph("POUFNE / CONFIDENTIAL", redFont);
                confidential.setAlignment(Element.ALIGN_CENTER);
                document.add(confidential);
            }

//            document.add(new Paragraph("Raport ąćźśćńółęĄĆÓŹŃĘŚŹŻ", font));
            Paragraph titleParagraph = new Paragraph(data.title(), font);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);
            document.add(new Paragraph("_________________________________________________________", font));
            document.add(new Paragraph("Autor: " + metadata.author(), font));
            document.add(new Paragraph("Data: " + metadata.createdAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), font));
            document.add(new Paragraph("_________________________________________________________", font));
            // Twój świetny pomysł ze Stream API!
            PdfPTable table = new PdfPTable(2);
            data.rows().entrySet().forEach(entry -> {
                table.addCell(new Paragraph(entry.getKey(), font));
                table.addCell(new Paragraph(entry.getValue().toString(), font));
            });
            document.add(table);
            document.add(new Paragraph("_________________________________________________________", font));
            document.add(new Paragraph("Suma: " + data.footerInfo(), font));

//            reportContent.lines()
//                    .forEach(line -> document.add(new Paragraph(line, font)));

            document.close();
        } catch (Exception e) {
            // Opakowujemy techniczny błąd w nasz własny wyjątek
            throw new ReportExportException("Nie udało się wygenerować PDF: " + e.getMessage());
        }
    }

}
