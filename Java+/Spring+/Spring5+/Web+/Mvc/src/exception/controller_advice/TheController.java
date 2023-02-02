package exception.controller_advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class TheController {
    static final String ENDPOINT = "/error";
    static final String EXCEPTION_MESSAGE = "the error!";

    @RequestMapping(ENDPOINT)
    public void responseStatus() {
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
