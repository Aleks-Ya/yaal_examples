package pdf;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static pdf.Helper.createPdfFile;

public class ReadPdfFileTest {

    @Test
    void blank() throws IOException {
        var file = createPdfFile();
        var randomAccessFile = new RandomAccessFile(file, "r");
        var parser = new PDFParser(randomAccessFile);
        parser.parse();
        var document = parser.getPDDocument();
        document.close();
    }
}
