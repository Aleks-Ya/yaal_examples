package ru.yaal.spring.mvc.controller.exception;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {
	@RequestMapping(value = "/throw_io_exception")
	@ExceptionHandler({ RuntimeException.class })
	public String throwIoException() throws IOException {
		throw new IOException("Input/output exception");
	}

	@RequestMapping(value = "/throw_enexpected_exception")
	public String throwUnexpectedException() throws IOException {
		throw new IllegalArgumentException("Expected exception");
	}
}
