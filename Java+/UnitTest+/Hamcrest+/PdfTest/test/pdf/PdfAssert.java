package pdf;

import com.codeborne.pdftest.PDF;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;

public class PdfAssert {

    @Test
    public void canAssertThatPdfContainsText() {
        URL pdfFile = PdfAssert.class.getResource("hello_world.pdf");
        PDF pdf = new PDF(pdfFile);
        assertThat(pdf, containsText("Hello World!"));
    }

}