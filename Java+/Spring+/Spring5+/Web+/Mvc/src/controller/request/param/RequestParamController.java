package controller.request.param;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class RequestParamController {
    static final String ENDPOINT = "/case/upper/";

    @ResponseBody
    @GetMapping(value = ENDPOINT)
    public String upperCase(@RequestParam String str) {
        return str.toUpperCase();
    }

}
