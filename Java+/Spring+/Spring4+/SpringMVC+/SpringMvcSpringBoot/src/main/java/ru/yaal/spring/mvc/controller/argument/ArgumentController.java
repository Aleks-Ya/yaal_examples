package ru.yaal.spring.mvc.controller.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArgumentController {
	@RequestMapping(value = "/argument")
	public ResponseEntity<String> agrument(HttpServletRequest request, HttpSession session,
			@RequestParam("code") String code) {

		int length = request.getContentLength();
		int maxInactiveInterval = session.getMaxInactiveInterval();
		String body = String.format("Content length: %d, Max inactive interval: %d, Parameter code: %s",
				length, maxInactiveInterval, code);

		return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
	}
}
