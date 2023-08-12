package pdf;

import org.apache.pdfbox.lucene.LucenePDFDocument;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ExtractTextTest {

    @Test
    @Disabled("Fail with 'NoClassDefFoundError: org/apache/pdfbox/exceptions/CryptographyException'")
    void blank() throws IOException {
        var file = Helper.createPdfFile();
        var luceneDocument = LucenePDFDocument.getDocument(file);// doesn't work
        var fields = luceneDocument.getFields();
        fields.toArray();
    }
}
