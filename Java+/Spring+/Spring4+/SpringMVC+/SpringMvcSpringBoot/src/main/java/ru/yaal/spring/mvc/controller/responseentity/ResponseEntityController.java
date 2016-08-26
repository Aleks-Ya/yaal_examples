package ru.yaal.spring.mvc.controller.responseentity;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResponseEntityController {
	@RequestMapping(value = "/respose_entity", method = RequestMethod.GET)
	public ResponseEntity<String> responseEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.put("my_header", Arrays.asList("my header value"));
		return new ResponseEntity<>("Body", headers, HttpStatus.ACCEPTED);
	}
}
