package security.authentication.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class PersonController {

    @GetMapping("/person")
    @ResponseBody
    public String person() {
        return "John";
    }
}
