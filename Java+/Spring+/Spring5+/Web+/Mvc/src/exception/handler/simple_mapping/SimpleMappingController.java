package exception.handler.simple_mapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;

@Controller
class SimpleMappingController {

    static final String FILE_NOT_FOUND_EXCEPTION = "/file_not_found_exception";
    static final String MISSING_SERVLET_PARAM_EXCEPTION = "/missing_servlet_request_parameter_exception";
    static final String NUMBER_FORMAT_EXCEPTION = "/number_format_exception";

    @RequestMapping(FILE_NOT_FOUND_EXCEPTION)
    public String fileNotFoundException() throws FileNotFoundException {
        throw new FileNotFoundException("File Not Found Exception");
    }

    @RequestMapping(NUMBER_FORMAT_EXCEPTION)
    public String numberFormatException() {
        throw new NumberFormatException("Number Format Exception");
    }

    @RequestMapping(MISSING_SERVLET_PARAM_EXCEPTION)
    public void missingServletRequestParameterException(@SuppressWarnings("unused") @RequestParam("abc") String param) {
    }
}
