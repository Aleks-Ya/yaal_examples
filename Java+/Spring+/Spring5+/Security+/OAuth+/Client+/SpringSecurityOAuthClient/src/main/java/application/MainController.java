package application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/allowed")
    public String allowed() {
        return "hello";
    }

    @RequestMapping("/disabled")
    public String disabled() {
        return "bye";
    }

}
