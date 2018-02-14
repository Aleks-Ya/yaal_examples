package controller.pdf.lowagie;

import com.codeborne.pdftest.PDF;
import common.BaseTest;
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
@ContextConfiguration(classes = {LowagiePdfController.class, UserPDFView.class})
public class LowagiePdfControllerTest extends BaseTest {

    @Test
    public void requestParam() throws Exception {
        MvcResult mvcResult = mvc.perform(
                get(LowagiePdfController.ENDPOINT)
                        .accept(MediaType.APPLICATION_PDF)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andReturn();

        byte[] body = mvcResult.getResponse().getContentAsByteArray();
        PDF bodyPdf = new PDF(body);
        assertThat(bodyPdf, containsText(LowagiePdfController.MESSAGE));
    }
}