package pdf;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static pdf.Helper.createPdfFile;

public class PDFTextStripperTest {

    @Test
    public void blank() throws IOException {
        File file = createPdfFile();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        PDFParser parser = new PDFParser(randomAccessFile);
        parser.parse();
        PDDocument document = parser.getPDDocument();

        COSDocument cosDocument = document.getDocument();
        COSObject catalog = cosDocument.getCatalog();
        assertThat(catalog.getObjectNumber(), equalTo(1L));

        document.close();
    }
}
