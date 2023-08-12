package pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

class Helper {
    static File createPdfFile() throws IOException {
        try (var document = new PDDocument()) {
            var page = new PDPage();
            document.addPage(page);

            var font = PDType1Font.HELVETICA_BOLD;

            var contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Hello World");
            contentStream.endText();

            contentStream.close();

            var file = File.createTempFile(ExtractTextTest.class.getSimpleName() + "_", ".pdf");
            System.out.println("Not blank: " + file.getAbsolutePath());
            document.save(file);
            return file;
        }

    }
}
