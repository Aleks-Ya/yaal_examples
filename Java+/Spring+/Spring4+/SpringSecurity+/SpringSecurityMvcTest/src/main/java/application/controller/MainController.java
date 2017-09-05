package application.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "root page";
    }

    @RequestMapping("/info")
    public String info() {
        return "info page";
    }

    @RequestMapping("/user")
    public String userAccessible() {
        return "This page is available to User";
    }

    @RequestMapping("/admin")
    public String adminAccessible() {
        return "This page is available to Admin";
    }

    @RequestMapping("/anonymous")
    public String anonymous() {
        return "This page is available to anyone";
    }

    @RequestMapping("/username")
    public String username() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return "username=" + username;
    }

    @RequestMapping("/principal")
    public String principal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "principal=" + principal;
    }
}
