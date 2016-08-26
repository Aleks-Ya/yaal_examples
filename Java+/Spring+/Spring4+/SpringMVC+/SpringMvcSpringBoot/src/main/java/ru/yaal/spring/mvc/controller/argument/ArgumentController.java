package ru.yaal.spring.mvc.controller.argument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArgumentController {
	@RequestMapping(value = "/http")
	public ResponseEntity<String> http(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		int length = request.getContentLength();
		String encoding = response.getCharacterEncoding();
		int maxInactiveInterval = session.getMaxInactiveInterval();
		String body = String.format("Content length: %d, Max inactive interval: %d, Encoding: %s", length,
				maxInactiveInterval, encoding);

		return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/request_param")
	public ResponseEntity<String> agrument(@RequestParam("code") String code) {
		return new ResponseEntity<>("Request param 'code': " + code, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/path_variable/{operation}/{id}")
	public ResponseEntity<String> pathVariable(@PathVariable("operation") String operationName, @PathVariable int id) {
		String body = String.format("Operation: %s, Id: %d", operationName, id);
		return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
	}
}
