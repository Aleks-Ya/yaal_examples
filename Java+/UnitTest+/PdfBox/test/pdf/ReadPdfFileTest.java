package pdf;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static pdf.Helper.createPdfFile;

public class ReadPdfFileTest {

    @Test
    public void blank() throws IOException {
        File file = createPdfFile();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        PDFParser parser = new PDFParser(randomAccessFile);
        parser.parse();
        PDDocument document = parser.getPDDocument();
        document.close();
    }
}
