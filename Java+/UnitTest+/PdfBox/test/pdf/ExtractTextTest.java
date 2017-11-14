package pdf;

import org.apache.lucene.document.Document;
import org.apache.pdfbox.lucene.LucenePDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExtractTextTest {
    private static File createDocument() throws IOException {
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

        File file = File.createTempFile(ExtractTextTest.class.getSimpleName() + "_", ".pdf");
        System.out.println("Not blank: " + file.getAbsolutePath());
        document.save(file);
        document.close();

        return file;

    }

    @Test
    public void blank() throws IOException {
        File file = createDocument();
        Document luceneDocument = LucenePDFDocument.getDocument(file);// doesn't work
        List fields = luceneDocument.getFields();
        fields.toArray();
    }
}
