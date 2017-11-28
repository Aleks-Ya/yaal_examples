package security.authorization.roles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    public static final String ADMIN_BODY = "This page is available to Admin";
    public static final String ANONYMOUS_BODY = "This page is available to anyone";

    @RequestMapping("/admin")
    public String adminAccessible() {
        return ADMIN_BODY;
    }

    @RequestMapping("/anonymous")
    public String anonymous() {
        return ANONYMOUS_BODY;
    }

}
