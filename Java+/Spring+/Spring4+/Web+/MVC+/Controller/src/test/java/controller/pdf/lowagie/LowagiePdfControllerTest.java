package controller.pdf.lowagie;

import com.codeborne.pdftest.PDF;
import controller.pdf.lowagie.LowagiePdfController;
import controller.pdf.lowagie.UserPDFView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.codeborne.pdftest.PDF.containsText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as String using @RequestBody.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {LowagiePdfController.class, UserPDFView.class})
public class LowagiePdfControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void requestParam() throws Exception {
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