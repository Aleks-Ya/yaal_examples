package controller.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
class RedirectController {
    static final String ENDPOINT_1 = "/first";
    static final String ENDPOINT_2 = "/second";

    @RequestMapping(value = ENDPOINT_1, method = GET)
    public String firstEndpoint(@RequestParam String message) {
        return format("redirect:%s?message=%s", ENDPOINT_2, message);
    }

    @ResponseBody
    @RequestMapping(value = ENDPOINT_2, method = GET)
    public String secondEndpoint(@RequestParam String message) {
        return "Second endpoint received message " + message;
    }

}
