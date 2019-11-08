package app.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    static final String ENDPOINT = "/hello";
    static final String CONTENT = "Hello, World!";

    @ResponseBody
    @GetMapping(ENDPOINT)
    public String hello() {
        return CONTENT;
    }
}
