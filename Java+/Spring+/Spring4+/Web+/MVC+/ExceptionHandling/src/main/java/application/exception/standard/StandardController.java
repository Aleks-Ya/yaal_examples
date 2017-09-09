package application.exception.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class StandardController {

    static final String MISSING_SERVLET_PARAM_EXCEPTION = "/missing_servlet_request_parameter_exception";

    @RequestMapping(MISSING_SERVLET_PARAM_EXCEPTION)
    public void missingServletRequestParameterException(
            @RequestParam("abc") String param
    ) {
    }

}
