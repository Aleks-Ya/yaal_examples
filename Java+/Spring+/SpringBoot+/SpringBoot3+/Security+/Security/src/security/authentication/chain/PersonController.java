package security.authentication.chain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class PersonController {

    @GetMapping("/person")
    @ResponseBody
    String person() {
        return "John";
    }

    @GetMapping("/insecure")
    @ResponseBody
    String insecure() {
        return "InsecureData";
    }
}
