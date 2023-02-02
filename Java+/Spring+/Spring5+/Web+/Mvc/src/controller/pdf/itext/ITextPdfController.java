package controller.pdf.itext;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
class ITextPdfController {

    static final String ENDPOINT = "/pdf";
    static final String MESSAGE = "Hello World!";

    @SuppressWarnings("unused")
    @RequestMapping(path = ITextPdfController.ENDPOINT, produces = MediaType.APPLICATION_PDF_VALUE)
    ModelAndView getPdf() {
        var model = new HashMap<String, String>();
        model.put("message", MESSAGE);
        return new ModelAndView(new ITextPdfView(), "userData", model);
    }

}
