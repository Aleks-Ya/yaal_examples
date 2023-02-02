package controller.pdf.lowagie;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * com.lowagie is deprecated since 2012 in favor of com.itextpdf.
 */
@Controller
class LowagiePdfController {

    static final String ENDPOINT = "/pdf";
    static final String MESSAGE = "Hello World!";

    @SuppressWarnings("unused")
    @RequestMapping(path = LowagiePdfController.ENDPOINT, produces = MediaType.APPLICATION_PDF_VALUE)
    ModelAndView getDdf() {
        var model = new HashMap<String, String>();
        model.put("message", MESSAGE);
        return new ModelAndView(new UserPDFView(), "userData", model);
    }

}
