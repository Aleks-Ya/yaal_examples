package pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class CreateDocumentTest {
    @Test
    public void blank() throws IOException {
        PDDocument document = new PDDocument();

        PDPage blankPage = new PDPage();
        document.addPage(blankPage);

        File file = File.createTempFile(CreateDocumentTest.class.getSimpleName() + "-blank-", ".pdf");
        System.out.println("Blank: " + file.getAbsolutePath());
        document.save(file);

        document.close();
    }

    @Test
    public void notBlank() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Hello World");
        contentStream.endText();

        contentStream.close();

        File file = File.createTempFile(CreateDocumentTest.class.getSimpleName() + "-not-blank-", ".pdf");
        System.out.println("Not blank: " + file.getAbsolutePath());
        document.save(file);
        document.close();

    }
}
