package pdf;

import org.apache.lucene.document.Document;
import org.apache.pdfbox.lucene.LucenePDFDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExtractTextTest {

    @Test
    public void blank() throws IOException {
        File file = Helper.createPdfFile();
        Document luceneDocument = LucenePDFDocument.getDocument(file);// doesn't work
        List fields = luceneDocument.getFields();
        fields.toArray();
    }
}
