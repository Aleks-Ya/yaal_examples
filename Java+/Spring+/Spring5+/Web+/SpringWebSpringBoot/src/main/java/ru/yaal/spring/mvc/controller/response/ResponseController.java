package ru.yaal.spring.mvc.controller.response;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Return a http-response instead of redirection to JSP.
 */
@Controller
public class ResponseController {
	
	/**
	 * Using ResponseEntity.
	 */
	@RequestMapping(value = "/respose_entity")
	public ResponseEntity<String> responseEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.put("my_header", Arrays.asList("my header value"));
		return new ResponseEntity<>("Body", headers, HttpStatus.ACCEPTED);
	}

	/**
	 * Using ResponseBody.
	 */
	@RequestMapping(value = "/respose_body", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String responseBody() {
		return "{message: Response body message}";
	}
}
