package security.authentication.password.encoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class PasswordController {

    static final String ENDPOINT = "/userAge";
    static final String RESPONSE_BODY = "30";

    @RequestMapping(ENDPOINT)
    public String anonymous() {
        return RESPONSE_BODY;
    }

}
