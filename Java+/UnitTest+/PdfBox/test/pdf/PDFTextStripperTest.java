package pdf;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static pdf.Helper.createPdfFile;

public class PDFTextStripperTest {

    @Test
    void blank() throws IOException {
        var file = createPdfFile();
        var randomAccessFile = new RandomAccessFile(file, "r");
        var parser = new PDFParser(randomAccessFile);
        parser.parse();
        try (var document = parser.getPDDocument()) {
            var cosDocument = document.getDocument();
            var catalog = cosDocument.getCatalog();
            assertThat(catalog.getObjectNumber(), equalTo(1L));
        }
    }
}
