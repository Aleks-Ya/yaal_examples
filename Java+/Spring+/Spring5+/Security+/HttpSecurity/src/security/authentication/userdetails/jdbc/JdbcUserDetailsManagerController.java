package security.authentication.userdetails.jdbc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
class JdbcUserDetailsManagerController {

    static final String ENDPOINT = "/jdbc";

    @ResponseBody
    @RequestMapping(ENDPOINT)
    public String anonymous(HttpSession session) {
        return session.getId();
    }

}
