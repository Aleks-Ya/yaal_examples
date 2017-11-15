package controller.pdf.itext;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
class ITextPdfController {

    static final String ENDPOINT = "/pdf";
    static final String MESSAGE = "Hello World!";

    @SuppressWarnings("unused")
    @RequestMapping(path = ITextPdfController.ENDPOINT, produces = MediaType.APPLICATION_PDF_VALUE)
    ModelAndView getPdf() {
        Map<String, String> model = new HashMap<>();
        model.put("message", MESSAGE);
        return new ModelAndView(new ITextPdfView(), "userData", model);
    }

}
