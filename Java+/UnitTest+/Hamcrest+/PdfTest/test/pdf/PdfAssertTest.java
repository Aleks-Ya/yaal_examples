package pdf;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.ResourceUtil.resourceToFile;

class PdfAssertTest {
    @Test
    void canAssertThatPdfContainsText() throws IOException {
        var pdfFile = resourceToFile("pdf/hello_world.pdf");
        var pdf = new PDF(pdfFile);
        assertThat(pdf, containsText("Hello World!"));
    }
}