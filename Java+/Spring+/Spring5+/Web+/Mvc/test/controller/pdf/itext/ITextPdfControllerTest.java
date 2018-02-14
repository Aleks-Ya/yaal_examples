package controller.pdf.itext;

import com.codeborne.pdftest.PDF;
import controller.BaseTest;
import controller.pdf.lowagie.UserPDFView;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import static com.codeborne.pdftest.PDF.containsText;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as String using @RequestBody.
 */
@ContextConfiguration(classes = {ITextPdfController.class, UserPDFView.class})
public class ITextPdfControllerTest extends BaseTest {

    @Test
    public void requestParam() throws Exception {
        MvcResult mvcResult = mvc.perform(get(ITextPdfController.ENDPOINT).accept(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andReturn();

        byte[] body = mvcResult.getResponse().getContentAsByteArray();
        PDF bodyPdf = new PDF(body);
        assertThat(bodyPdf, containsText(ITextPdfController.MESSAGE));
    }
}