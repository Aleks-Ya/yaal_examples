package ru.yaal.spring.security.application.controller;

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
}
