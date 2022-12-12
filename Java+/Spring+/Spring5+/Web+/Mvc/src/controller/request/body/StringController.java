package controller.request.body;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class StringController {
    static final String ENDPOINT = "/string";

    @ResponseBody
    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public String post(@RequestBody String body) {
        return "body=" + body;
    }

}
