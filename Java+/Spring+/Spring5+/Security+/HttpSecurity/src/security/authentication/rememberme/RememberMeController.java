package security.authentication.rememberme;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
class RememberMeController {

    static final String ENDPOINT = "/rememberMe";

    @ResponseBody
    @RequestMapping(ENDPOINT)
    public String anonymous(HttpSession session) {
        return session.getId();
    }

}
