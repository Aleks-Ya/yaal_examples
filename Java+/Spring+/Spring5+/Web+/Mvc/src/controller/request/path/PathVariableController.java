package controller.request.path;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class PathVariableController {
    static final String ENDPOINT = "/case/upper/";

    @ResponseBody
    @GetMapping(value = ENDPOINT + "{str}")
    public String upperCase(@PathVariable String str) {
        return str.toUpperCase();
    }

}
