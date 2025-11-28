package odf.writer;

import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import util.FileUtil;

class WriterTest {

    @Test
    void createOdt() throws Exception {
        try (var doc = OdfTextDocument.newTextDocument()) {
            doc.addText("This is my very first ODF test");
            var file = FileUtil.createAbsentTempFile(".odt");
            System.out.println("Output file: " + file.getAbsolutePath());
            doc.save(file);
        }
    }
}