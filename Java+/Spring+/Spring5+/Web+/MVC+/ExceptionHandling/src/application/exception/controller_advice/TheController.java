package application.exception.controller_advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;

@Controller
class TheController {

    static final String ENDPOINT = "/error";
    static final String EXCEPTION_MESSAGE = "the error!";

    @RequestMapping(ENDPOINT)
    public void responseStatus() {
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
