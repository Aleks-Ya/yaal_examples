package rst.controller;

import rst.service.MyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {
    public MyController(MyService service) {
        this.service = service;
    }

    MyService service;

    @RequestMapping("/hello")
    public String hello(final Principal principal) {
        return service.greeting() + principal.getName();
    }
}
