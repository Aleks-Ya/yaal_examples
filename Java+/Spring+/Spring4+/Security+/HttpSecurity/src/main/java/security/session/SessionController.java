package security.session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
class SessionController {

    static final String ENDPOINT = "/session";

    @ResponseBody
    @RequestMapping(ENDPOINT)
    public String anonymous(HttpSession session) {
        return session.getId();
    }

}
