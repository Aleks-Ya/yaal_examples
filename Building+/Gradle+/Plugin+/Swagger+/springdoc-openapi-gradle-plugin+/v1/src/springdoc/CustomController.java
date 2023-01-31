package springdoc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class CustomController {
    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public String custom() {
        return "custom";
    }

    @RequestMapping(value = "/upperCase/{str}", method = RequestMethod.GET)
    public String upperCase(@PathVariable String str) {
        return str.toUpperCase();
    }
}
