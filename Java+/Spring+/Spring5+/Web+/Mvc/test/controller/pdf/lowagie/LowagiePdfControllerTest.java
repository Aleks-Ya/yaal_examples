package controller.pdf.lowagie;

import com.codeborne.pdftest.PDF;
import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as String using @RequestBody.
 */
@ContextConfiguration(classes = {LowagiePdfController.class, UserPDFView.class})
class LowagiePdfControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        var mvcResult = mvc.perform(
                        get(LowagiePdfController.ENDPOINT)
                                .accept(MediaType.APPLICATION_PDF)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andReturn();

        var body = mvcResult.getResponse().getContentAsByteArray();
        var bodyPdf = new PDF(body);
        assertThat(bodyPdf, containsText(LowagiePdfController.MESSAGE));
    }
}