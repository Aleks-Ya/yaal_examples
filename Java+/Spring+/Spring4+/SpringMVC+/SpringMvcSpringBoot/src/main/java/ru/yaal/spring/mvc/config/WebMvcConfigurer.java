package ru.yaal.spring.mvc.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ru.yaal.spring.mvc.controller.argument.RequestBodyController.UserConverter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private UserConverter converter;

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		assert converter != null;
		converters.add(converter);
	}
}
