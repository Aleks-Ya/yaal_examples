package security.application.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping("/")
	@ResponseBody
	public String root() {
		return "root page";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/info")
	public String info() {
		return "info";
	}

	@RequestMapping("/user")
	public String userAccessible() {
		return "user_accessible";
	}

	@RequestMapping("/admin")
	public String adminAccessible() {
		return "admin_accessible";
	}
	
	@RequestMapping("/anonymus")
	public String anonymus() {
		return "anonymus";
	}

	@ResponseBody
	@RequestMapping("/username")
	public String username() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return "username=" + username;
	}

	@ResponseBody
	@RequestMapping("/principal")
	public String principal() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "principal=" + principal;
	}
}
